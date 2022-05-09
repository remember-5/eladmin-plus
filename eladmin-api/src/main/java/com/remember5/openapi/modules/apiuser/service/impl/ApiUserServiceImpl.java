package com.remember5.openapi.modules.apiuser.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSONObject;
import com.remember5.captcha.entity.CaptchaTypeEnum;
import com.remember5.captcha.utils.CaptchaUtils;
import com.remember5.openapi.constant.RedisKeyConstant;
import com.remember5.openapi.modules.apiuser.domain.ApiUser;
import com.remember5.openapi.modules.apiuser.repository.ApiUserRepository;
import com.remember5.openapi.modules.apiuser.service.ApiUserService;
import com.remember5.openapi.modules.apiuser.service.dto.ApiUserDto;
import com.remember5.openapi.modules.apiuser.service.dto.LoginUser;
import com.remember5.openapi.modules.apiuser.service.mapstruct.ApiUserMapper;
import com.remember5.openapi.util.JwtTokenUtils;
import com.remember5.redis.utils.RedisUtils;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.properties.RsaProperties;
import me.zhengjie.properties.JwtProperties;
import me.zhengjie.result.R;
import me.zhengjie.result.REnum;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.ValidationUtil;
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
    private final JwtTokenUtils jwtTokenUtils;
    private final JwtProperties jwtProperties;
    private final RedisUtils redisUtils;
    private final RsaProperties rsaProperties;
    private final CaptchaUtils captchaUtils;

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
            accessToken = jwtTokenUtils.generateToken(apiUserDto);
//            RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);
            // 生成accessToken   保存到redis key = JWT:TOKEN:ACCESS:
            redisUtils.set(RedisKeyConstant.USER_ACCESS_TOKEN + accessToken, accessToken, jwtProperties.getTokenValidityInSeconds());
            tokenMap.put("access_token", accessToken);
            // 生成refreshToken  保存到redis key = JWT:TOKEN:REFRESH:
            refreshToken = jwtTokenUtils.generateToken(apiUserDto);
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
        if (captcha.getCharType() - 1 == CaptchaTypeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
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
    public boolean phoneExits(String phone) {
        long num = apiUserRepository.countByPhone(phone);
        return num>0;
    }
}
