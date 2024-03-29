/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.remember5.system.modules.security.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.remember5.captcha.constants.CaptchaTypeEnum;
import com.remember5.captcha.core.CaptchaCodeProvider;
import com.remember5.captcha.properties.CaptchaCodeProperties;
import com.remember5.core.exception.BadRequestException;
import com.remember5.core.properties.RsaProperties;
import com.remember5.core.utils.StringUtils;
import com.remember5.redis.utils.RedisUtils;
import com.remember5.security.properties.JwtProperties;
import com.remember5.security.utils.TokenProvider;
import com.remember5.system.modules.logging.annotation.Log;
import com.remember5.system.modules.security.service.OnlineUserService;
import com.remember5.system.modules.security.service.dto.AuthUserDto;
import com.remember5.system.modules.security.service.dto.JwtUserDto;
import com.remember5.system.modules.system.domain.User;
import com.remember5.system.properties.LoginProperties;
import com.wf.captcha.base.Captcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "系统：系统授权接口")
public class AuthorizationController {
    private final RsaProperties rsaProperties;
    private final JwtProperties jwtProperties;
    private final RedisUtils redisUtils;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final LoginProperties loginProperties;
    private final CaptchaCodeProvider captchaCodeProvider;
    private final CaptchaCodeProperties captchaCodeProperties;

    @Log("用户登录")
    @Operation(summary = "登录授权")
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) {
        RSA rsa = new RSA(rsaProperties.getPrivateKey(), null);
        // 密码解密
        String password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));
        // 查询验证码
        String code = (String) redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.del(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        final User user = jwtUserDto.getUser();
        String token = tokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getPhone());
        // 保存在线信息
        onlineUserService.save(jwtUserDto, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<>(2);
        authInfo.put("token", jwtProperties.getTokenStartWith() + token);
        authInfo.put("user", jwtUserDto);
        // todo 删除缓存
        if (loginProperties.isSingleLogin()) {
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
        }
        // 保存在线信息
        onlineUserService.save(jwtUserDto, token, request);
        // 返回登录信息
        return ResponseEntity.ok(authInfo);
    }


    @Operation(summary = "获取验证码")
    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        // 获取运算的结果
        Captcha captcha = captchaCodeProvider.getCaptcha();
        String uuid = jwtProperties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == CaptchaTypeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisUtils.set(uuid, captchaValue, captchaCodeProperties.getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<>(2);
        imgResult.put("img", captcha.toBase64());
        imgResult.put("uuid", uuid);
        return ResponseEntity.ok(imgResult);
    }

    @Operation(summary = "退出登录")
    @DeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout() {
        onlineUserService.logout(tokenProvider.getTokenByRequest());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
