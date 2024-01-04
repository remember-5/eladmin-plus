package com.remember5.openapi.modules.auth.rest;

import com.remember5.core.result.R;
import com.remember5.openapi.modules.auth.domain.*;
import com.remember5.openapi.modules.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 认证管理
 *
 * @author wangjiahao
 * @date 2023/12/06
 */
@Slf4j
@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "账号密码登录")
    @PostMapping(value = "/login")
    public R<TokenDTO> login(@RequestBody @Validated UsernameLogin usernameLogin) {
        return R.success(authService.login(usernameLogin));
    }

    @Operation(summary = "手机验证码登录")
    @PostMapping(value = "/loginBySMS")
    public R<TokenDTO> loginBySMS(@RequestBody @Validated PhoneLogin phoneLogin) {
        return R.success(authService.loginBySMS(phoneLogin));
    }

    @Operation(summary = "微信登录")
    @PostMapping(value = "/loginByWechat")
    public R<TokenDTO> loginByWechat(@RequestBody @Validated WechatLogin wechatLogin) {
        return R.success(authService.loginByWechat(wechatLogin));
    }

    @Operation(summary = "注册")
    @PostMapping(value = "register")
    public R<Boolean> register(@RequestBody @Validated UsernameRegister usernameRegister) {
        return R.success(authService.register(usernameRegister));
    }

    @Operation(summary = "登出")
    @PostMapping(value = "logout")
    public R<Boolean> logout() {
        return R.success();
    }

    @Operation(summary = "获取图形验证码")
    @GetMapping(value = "code")
    public R<CaptchaDTO> getCode() {
        return R.success(authService.getCode());
    }

}
