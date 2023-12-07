/**
 * Copyright [2022] [remember5]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.remember5.openapi.modules.auth.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.remember5.captcha.CaptchaTypeEnum;
import com.remember5.captcha.CaptchaUtils;
import com.remember5.core.exception.ServiceException;
import com.remember5.core.properties.RsaProperties;
import com.remember5.core.result.REnum;
import com.remember5.openapi.constant.RedisKeyConstant;
import com.remember5.openapi.modules.apiuser.domain.ApiUser;
import com.remember5.openapi.modules.apiuser.repository.ApiUserRepository;
import com.remember5.openapi.modules.auth.domain.*;
import com.remember5.openapi.modules.auth.service.AuthService;
import com.remember5.security.properties.JwtProperties;
import com.remember5.redis.utils.RedisUtils;
import com.remember5.security.utils.TokenProvider;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjiahao
 * @date 2023/12/7 13:28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ApiUserRepository apiUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final RedisUtils redisUtils;
    private final RsaProperties rsaProperties;
    private final CaptchaUtils captchaUtils;
    private final WxMaService wxMaService;

    /**
     * 生成token
     *
     * @param id       用户id
     * @param username 用户名
     * @param phone    手机号
     * @return TokenDTO
     */
    public TokenDTO generateToken(Long id, String username, String phone) {
        TokenDTO tokenDTO = new TokenDTO();
        String accessToken = tokenProvider.createAccessToken(id, username, phone);
        // 生成accessToken   保存到redis key = JWT:TOKEN:ACCESS:
        redisUtils.set(RedisKeyConstant.USER_ACCESS_TOKEN + accessToken, accessToken, jwtProperties.getTokenValidityInSeconds());
        tokenDTO.setAccessToken(accessToken);
        return tokenDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(UsernameRegister usernameRegister) {
        if (!redisUtils.hasKey(usernameRegister.getUuid())) {
            throw new ServiceException(REnum.A0242);
        }

        String code = (String) redisUtils.get(usernameRegister.getUuid());
        redisUtils.del(usernameRegister.getUuid());

        if (!usernameRegister.getCode().equalsIgnoreCase(code)) {
            throw new ServiceException(REnum.A0240);
        }

        ApiUser apiUser = new ApiUser();
        apiUser.setUsername(usernameRegister.getUsername());

        RSA rsa = new RSA(rsaProperties.getPrivateKey(), null);
        apiUser.setPassword(passwordEncoder.encode(Arrays.toString(rsa.decrypt(usernameRegister.getPassword(), KeyType.PrivateKey))));
        apiUser.setIsDeleted(false);
        apiUserRepository.save(apiUser);
        return true;
    }


    @Override
    public CaptchaDTO getCode() {
        Captcha captcha = captchaUtils.getCaptcha();
        String uuid = jwtProperties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == CaptchaTypeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        redisUtils.set(uuid, captchaValue, captchaUtils.getCaptchaCode().getExpiration(), TimeUnit.MINUTES);

        final CaptchaDTO captchaDTO = new CaptchaDTO();
        captchaDTO.setImg(captcha.toBase64());
        captchaDTO.setUuid(uuid);
        return captchaDTO;
    }

    @Override
    public TokenDTO login(UsernameLogin usernameLogin) {
        final boolean hasUUID = redisUtils.hasKey(usernameLogin.getUuid());
        if (!hasUUID) {
            throw new ServiceException(REnum.A0242);
        }

        String code = (String) redisUtils.get(usernameLogin.getUuid());
        redisUtils.del(usernameLogin.getUuid());
        if (!usernameLogin.getCode().equalsIgnoreCase(code)) {
            throw new ServiceException(REnum.A0240);
        }
        final ApiUser apiUser = apiUserRepository.findByUsername(usernameLogin.getUsername());
        if (ObjectUtil.isNull(apiUser)) {
            throw new ServiceException(REnum.A0201);
        }

        RSA rsa = new RSA(rsaProperties.getPrivateKey(), null);
        String password = Arrays.toString(rsa.decrypt(usernameLogin.getPassword(), KeyType.PrivateKey));
        if (!passwordEncoder.matches(password, apiUser.getPassword())) {
            throw new ServiceException(REnum.A0210);
        }

        return generateToken(apiUser.getId(), apiUser.getUsername(), apiUser.getPhone());
    }


    @Override
    public TokenDTO loginBySMS(PhoneLogin phoneLogin) {
        final ApiUser apiUser = apiUserRepository.findByPhone(phoneLogin.getPhone());
        return generateToken(apiUser.getId(), apiUser.getUsername(), apiUser.getPhone());
    }

    @Override
    public TokenDTO loginByWechat(WechatLogin wechatLogin) {
        try {
            final WxMaJscode2SessionResult sessionResult = wxMaService.jsCode2SessionInfo(wechatLogin.getCode());
            final WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService()
                    .getPhoneNoInfo(sessionResult.getSessionKey(), wechatLogin.getEncryptedData(), wechatLogin.getIv());
            final String openid = sessionResult.getOpenid();
            final String purePhoneNumber = phoneNoInfo.getPurePhoneNumber();
            ApiUser apiUser = apiUserRepository.findByPhone(purePhoneNumber);
            if (null == apiUser) {
                apiUser = new ApiUser();
                apiUser.setUsername(purePhoneNumber);
                apiUser.setPhone(purePhoneNumber);
                apiUser.setIsDeleted(false);
                apiUserRepository.save(apiUser);
            }
            return generateToken(apiUser.getId(), apiUser.getUsername(), apiUser.getPhone());
        } catch (WxErrorException e) {
            log.error("微信一键登录失败 {}", e.getMessage());
            throw new ServiceException(REnum.A0200);
        }

    }

    @Override
    public boolean logout() {
        return false;
    }

}
