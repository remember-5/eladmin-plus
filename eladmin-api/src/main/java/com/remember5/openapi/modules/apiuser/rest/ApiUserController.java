package com.remember5.openapi.modules.apiuser.rest;

import com.remember5.core.annotation.rest.AnonymousGetMapping;
import com.remember5.core.annotation.rest.AnonymousPostMapping;
import com.remember5.core.result.R;
import com.remember5.core.result.REnum;
import com.remember5.core.utils.ValidationUtil;
import com.remember5.openapi.constant.RedisKeyConstant;
import com.remember5.openapi.modules.apiuser.domain.WxLoginUser;
import com.remember5.openapi.modules.apiuser.service.ApiUserService;
import com.remember5.openapi.modules.apiuser.service.dto.LoginUser;
import com.remember5.redis.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * @author wangjiahao
 */
@Slf4j
@Api(tags = "API用户管理")
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class ApiUserController {

    private final ApiUserService apiUserService;
    private final RedisUtils redisUtils;

    @ApiOperation("注册")
    @PostMapping(value = "register")
    public R register(@RequestBody @Validated LoginUser user) {
        return apiUserService.register(user);
    }

    @ApiOperation("登出")
    @PostMapping(value = "logout")
    public R logout() {
        // TODO 登出,删除redis和token
        return R.success();
    }

    @ApiOperation("账号密码登录")
    @PostMapping(value = "loginByAccount")
    public R loginByAccount(@RequestBody LoginUser user) {
        return apiUserService.loginByAccount(user);
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public R<Object> getUserInfo() {
        return R.success();
    }

    @ApiOperation("手机验证码登录")
    @PostMapping(value = "loginByPhoneCaptcha")
    public R loginBySms(@RequestBody LoginUser user) {
        return apiUserService.loginBySms(user);
    }

    @ApiOperation("微信小程序一键登录")
    @PostMapping(value = "wxMiniAppLogin")
    public R wxMiniAppLogin(@RequestBody WxLoginUser wxLoginInfo) {
        return apiUserService.wxMiniAppLogin(wxLoginInfo);
    }

    @ApiOperation("支付宝一键登录")
    @PostMapping(value = "loginByZfb")
    public R loginByZfb(@RequestBody LoginUser user) {
//        return apiUserService.loginByZfb(user);
        return R.success();
    }

    @ApiOperation("Token续期")
    @PostMapping(value = "refToken")
    public R refToken(HttpServletResponse response) {
        // TODO Token续期
        return R.success();
    }

    @ApiOperation("获取图形验证码")
    @AnonymousGetMapping(value = "code")
    public R getCode() {
        return apiUserService.getCode();
    }

    @ApiOperation("获取短信验证码")
    @AnonymousPostMapping(value = "smsCode")
    public R getsmsCode(@RequestBody LoginUser user) {
        if (!ValidationUtil.isPhone(user.getPhone())) {
            return R.fail(REnum.A0151);
        }
        String redisKey = RedisKeyConstant.CAPTCHA_KEY + user.getPhone();
        if (!redisUtils.hasKey(redisKey)) {
//            String captchaCode = RandomUtil.randomNumbers(4);
//            AliyunSMS.send(captchaCode, phone);
            String captchaCode = "1234";
            boolean as = redisUtils.set(redisKey, captchaCode, RedisKeyConstant.CAPTCHA_KEY_INVALID);
            return as ? R.success() : R.fail(REnum.A0001);
        } else {
            return R.fail(REnum.E0002);
        }
    }

    @ApiOperation("注销")
    @GetMapping(value = "deleted")
    public R deleted(Long userId) {
        return apiUserService.deleted(userId);
    }

    @ApiOperation("手机号注册获取短信验证码")
    @GetMapping(value = "captchaByResetPassword/{phone}")
    public R phoneCaptcha(@PathVariable String phone) {
        if (!ValidationUtil.isPhone(phone)) {
            return R.fail(REnum.A0151);
        }
        String redisKey = RedisKeyConstant.CAPTCHA_KEY + phone;
        if (!redisUtils.hasKey(redisKey)) {
//            String captchaCode = RandomUtil.randomNumbers(4);
//            AliyunSMS.send(captchaCode, phone);
            String captchaCode = "1234";
            boolean as = redisUtils.set(redisKey, captchaCode, RedisKeyConstant.CAPTCHA_KEY_INVALID);
            return as ? R.success() : R.fail(REnum.A0001);
        } else {
            return R.fail(REnum.E0002);
        }
    }

    /**
     * 忘记密码-发送短信验证码
     *
     * @param phone 手机号
     * @return /
     */
    @ApiOperation("忘记密码-发送短信验证码")
    @GetMapping("forgetByPhone/{phone}")
    public R resetCaptcha(@PathVariable @NotNull String phone) {
        return apiUserService.captchaByResetPassword(phone);
    }

    /**
     * 忘记密码
     *
     * @param user 用户信息
     * @return /
     */
    @ApiOperation("忘记密码")
    @PostMapping("resetPassword")
    public R resetPass(@RequestBody LoginUser user) {
        return apiUserService.forgetPassword(user);
    }

}
