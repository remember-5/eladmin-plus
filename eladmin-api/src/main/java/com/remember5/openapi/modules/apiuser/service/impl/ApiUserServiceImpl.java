package com.remember5.openapi.modules.apiuser.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.JsonSyntaxException;
import com.remember5.captcha.CaptchaTypeEnum;
import com.remember5.captcha.CaptchaUtils;
import com.remember5.core.properties.RsaProperties;
import com.remember5.core.result.R;
import com.remember5.core.result.REnum;
import com.remember5.core.utils.StringUtils;
import com.remember5.core.utils.ValidationUtil;
import com.remember5.openapi.constant.RedisKeyConstant;
import com.remember5.openapi.entity.WxLoginResult;
import com.remember5.openapi.modules.apiuser.domain.ApiUser;
import com.remember5.openapi.modules.apiuser.domain.WxLoginUser;
import com.remember5.openapi.modules.apiuser.repository.ApiUserRepository;
import com.remember5.openapi.modules.apiuser.service.ApiUserService;
import com.remember5.openapi.modules.apiuser.service.dto.ApiUserDto;
import com.remember5.openapi.modules.apiuser.service.dto.LoginUser;
import com.remember5.openapi.modules.apiuser.service.mapstruct.ApiUserMapper;
import com.remember5.redis.properties.JwtProperties;
import com.remember5.redis.utils.RedisUtils;
import com.remember5.redis.utils.TokenProvider;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author fly
 * @description
 * @date 2021/8/20 18:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiUserServiceImpl implements ApiUserService {

    private final ApiUserRepository apiUserRepository;
    private final ApiUserMapper apiUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final RedisUtils redisUtils;
    private final RsaProperties rsaProperties;
    private final CaptchaUtils captchaUtils;
    private final WxMaService wxMaService;

    @Override
    public ApiUserDto findByPhone(String phone) {
        return apiUserMapper.toDto(apiUserRepository.findByPhone(phone));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiUserDto create(ApiUser resources) {
        return apiUserMapper.toDto(apiUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ApiUser resources) {
        ApiUser apiUser = apiUserRepository.findById(resources.getId()).orElseGet(ApiUser::new);
        ValidationUtil.isNull(apiUser.getId(), "ApiUser", "id", resources.getId());
        apiUser.copy(resources);
        apiUserRepository.save(apiUser);
    }

    public R getTokens(LoginUser user, ApiUser apiUser) {
        // 获取token
        String accessToken;
        String refreshToken;
        Map<String, Object> tokenMap = new HashMap<>(4);
        try {
            ApiUserDto apiUserDto = findByPhone(user.getPhone());
//            ApiUserDto apiUserDto = apiUserMapper.toDto(apiUser);
            accessToken = tokenProvider.createAccessToken(apiUserDto.getPhone(), apiUserDto.getUsername());
//            RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);
            // 生成accessToken   保存到redis key = JWT:TOKEN:ACCESS:
            redisUtils.set(RedisKeyConstant.USER_ACCESS_TOKEN + accessToken, accessToken, jwtProperties.getTokenValidityInSeconds());
            tokenMap.put("access_token", accessToken);
            // 生成refreshToken  保存到redis key = JWT:TOKEN:REFRESH:
            refreshToken = tokenProvider.createAccessToken(apiUserDto.getPhone(), apiUserDto.getUsername());
            redisUtils.set(RedisKeyConstant.USER_REFRESH_TOKEN + refreshToken, refreshToken, jwtProperties.getTokenValidityInSeconds());
            tokenMap.put("refresh_token", refreshToken);
            tokenMap.put("user_info", apiUser);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return R.success(tokenMap);
    }

    @Override
    public R register(LoginUser user) {

        // 数据为空 注册异常
        if (ObjectUtil.isNull(user)) {
            return R.fail(REnum.A0100);
        }
        // 查询验证码
        String code = (String) redisUtils.get(user.getUuid());
        // 清除验证码
        redisUtils.del(user.getUuid());
        if (StringUtils.isBlank(code)) {
            // 验证码不存在或已过期
            return R.fail(REnum.A0242);
        }
        if (StringUtils.isBlank(user.getCode()) || !user.getCode().equalsIgnoreCase(code)) {
            // 验证码错误
            return R.fail(REnum.A0240);
        }
        // 无输入密码
        if (StrUtil.isBlank(user.getPassword())) {
            return R.fail(REnum.A0210);
        }

        final String smsCode = user.getSmsCode();
        final String phone = user.getPhone();
        String redisKey = RedisKeyConstant.CAPTCHA_KEY + phone;
        // 判断是否存在key
        if (!redisUtils.hasKey(redisKey)) {
            return R.fail(REnum.A0242);
        }

        if (!smsCode.equals(redisUtils.get(redisKey))) {
            return R.fail(REnum.A0240);
        }
        redisUtils.del(redisKey);

        ApiUser apiUser = new ApiUser();
        // 检查手机号
        if (StrUtil.isNotBlank(user.getPhone())) {
            if (ObjectUtil.isNotNull(findByPhone(user.getPhone()))) {
                // 已有手机号 不可重复注册
                return R.fail(REnum.A0207);
            }
        } else {
            // 没有手机号
            return R.fail(REnum.A0206);
        }
        apiUser.setPhone(user.getPhone());
        // 按需填入字段
        apiUser.setUsername(StrUtil.isNotBlank(user.getUsername()) ? user.getUsername() : null);

        // 密码解密
        RSA rsa = new RSA(rsaProperties.getPrivateKey(), null);
        apiUser.setPassword(passwordEncoder.encode(new String(rsa.decrypt(user.getPassword(), KeyType.PrivateKey))));

        apiUser.setIsDeleted(false);
        create(apiUser);
        return R.fail(REnum.A00000);
    }

    public ApiUserDto create(JSONObject jsonObject) {
        ApiUserDto purePhoneNumber = findByPhone(jsonObject.getString("purePhoneNumber"));
        if (ObjectUtil.isNotNull(purePhoneNumber)) {
            return purePhoneNumber;
        }
        ApiUser apiUser = new ApiUser();
        apiUser.setPhone(jsonObject.getString("purePhoneNumber"));
        // 按需填入字段
        apiUser.setUsername(jsonObject.getString("purePhoneNumber"));

//        apiUser.setWxOpenid(jsonObject.getString("wxopenid"));
//        apiUser.setAliOpenid(jsonObject.getString("aliopenid"));
        apiUser.setIsDeleted(false);
        ApiUserDto apiUserDto = create(apiUser);
        return apiUserDto;
    }

    @Override
    public R getCode() {
        // 获取运算的结果
        Captcha captcha = captchaUtils.getCaptcha();
        String uuid = jwtProperties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == CaptchaTypeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisUtils.set(uuid, captchaValue, captchaUtils.getCaptchaCode().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return R.success(imgResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R deleted(Long userId) {
        // TODO 删除用户相关数据
        apiUserRepository.deleteById(userId);
        return R.success();
    }

    @Override
    public R loginByAccount(LoginUser user) {
        ApiUser apiUser = new ApiUser();
        // 手机号密码 + 图形验证码
        // 查询验证码
        String code = (String) redisUtils.get(user.getUuid());
        // 清除验证码
        redisUtils.del(user.getUuid());
        if (StringUtils.isBlank(code)) {
            return R.fail(REnum.A0242);
        }
        if (StringUtils.isBlank(user.getCode()) || !user.getCode().equalsIgnoreCase(code)) {
            return R.fail(REnum.A0240);
        }
        // 获取数据库中的user
        apiUser = apiUserMapper.toEntity(findByPhone(user.getPhone()));
        if (ObjectUtil.isNull(apiUser)) {
            // 无此用户
            return R.fail(REnum.A0201);
        }
        // 密码解密
        RSA rsa = new RSA(rsaProperties.getPrivateKey(), null);
        String password = new String(rsa.decrypt(user.getPassword(), KeyType.PrivateKey));
        if (!passwordEncoder.matches(password, apiUser.getPassword())) {
            // 密码错误
            return R.fail(REnum.A0210);
        }
        return getTokens(user, apiUser);
    }


    @Override
    public R loginBySms(LoginUser user) {
        ApiUser apiUser = new ApiUser();
        // 手机号验证码
        // TODO 对接短信接口从redis中取出验证码
        String smsCode = "1234";
        if (user.getSmsCode().equals(smsCode)) {
            apiUser = apiUserMapper.toEntity(findByPhone(user.getPhone()));
            if (ObjectUtil.isNotNull(apiUser)) {
                return getTokens(user, apiUser);
            }
            // 无此用户
            return R.fail(REnum.A0201);
        }
        // 短信验证码错误
        return R.fail(REnum.A0131);
    }

    @Override
    public R captchaByResetPassword(String phone) {

        // 正则校验手机号
        final boolean mobile = Validator.isMobile(phone);
        if (!mobile) {
            return R.fail(REnum.A0206);
        }

        // 判断手机号是否正确，是否存在数据库
        final boolean existsPhone = apiUserRepository.existsByPhone(phone);
        if (!existsPhone) {
            return R.fail(REnum.A0206);
        }

        String redisKey = RedisKeyConstant.RESET_PWD_CAPTCHA_KEY + phone;
        if (!redisUtils.hasKey(redisKey)) {
            String uuid = UUID.randomUUID().toString(true);
//            String captchaCode = RandomUtil.randomNumbers(4);
//            AliyunSMS.send(captchaCode, phone);
            String captchaCode = "1234";
            // set验证码到redis
            boolean as = redisUtils.set(redisKey, captchaCode, RedisKeyConstant.RESET_PWD_CAPTCHA_INVALID);
            return as ? R.success(uuid) : R.fail(REnum.A0001);
        }
        return R.fail(REnum.E0002);
    }

    @Override
    public R forgetPassword (LoginUser user) {
        if (ObjectUtil.isNull(user) || ObjectUtil.isNull(user.getPassword()) || ObjectUtil.isNull(user.getPhone())) {
            return R.fail(REnum.A0400);
        }
        String phone = user.getPhone();
        // 正则校验手机号
        final boolean mobile = Validator.isMobile(phone);
        if (!mobile) {
            return R.fail(REnum.A0206);
        }

        // 判断手机号是否正确，是否存在数据库
        final boolean existsPhone = apiUserRepository.existsByPhone(phone);
        if (!existsPhone) {
            return R.fail(REnum.A0206);
        }
        apiUserRepository.updatePass(phone, passwordEncoder.encode(user.getPassword()));
        return R.success();
    }

    /**
     * sessionId+encrypt+iv换取其他信息
     *
     * @param wxLoginInfo /
     * @return
     */
    @Override
    public R wxMiniAppLogin(WxLoginUser wxLoginInfo) {
        try {
            // 微信一键登录
            final WxLoginResult wxLoginResult = wechatMiniProgramLogin(wxLoginInfo.getCode(), wxLoginInfo.getEncryptedData(), wxLoginInfo.getIv());
            if (null == wxLoginResult) {
                return R.fail(REnum.A0100);
            }
            final String openid = wxLoginResult.getOpenid();
            final String purePhoneNumber = wxLoginResult.getPurePhoneNumber();
            // Todo 保存用户的信息
            // 删除用户的openid
            return R.success(purePhoneNumber);
            // session_key 不存在，已经过期
        } catch (JsonSyntaxException e) {
            return R.fail(REnum.A0001.code, "", "请重新授权！");
        }
    }


    /**
     * 微信小程序的一键登录
     *
     * @param code          code
     * @param encryptedData 加密数据
     * @param iv            iv
     * @return 登陆结果
     */
    private WxLoginResult wechatMiniProgramLogin(String code, String encryptedData, String iv) {
        try {
            final WxMaJscode2SessionResult sessionResult = wxMaService.jsCode2SessionInfo(code);
            final WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionResult.getSessionKey(), encryptedData, iv);

            return WxLoginResult.builder().openid(sessionResult.getOpenid())
                    .unionid(sessionResult.getUnionid())
                    .sessionKey(sessionResult.getSessionKey())
                    .phoneNumber(phoneNoInfo.getPhoneNumber())
                    .purePhoneNumber(phoneNoInfo.getPurePhoneNumber()).build();
        } catch (WxErrorException e) {
            log.error("微信一键登录失败 {}", e.getMessage());
            return null;
        }

    }

}
