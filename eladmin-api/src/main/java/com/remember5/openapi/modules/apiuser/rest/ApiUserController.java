package com.remember5.openapi.modules.apiuser.rest;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ReUtil;
import com.remember5.openapi.constant.RedisKeyConstant;
import com.remember5.openapi.modules.apiuser.service.ApiUserService;
import com.remember5.openapi.modules.apiuser.service.dto.LoginUser;
import com.remember5.redis.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.Log;
import me.zhengjie.result.R;
import me.zhengjie.result.REnum;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    @Log("注册")
    @ApiOperation("注册")
    @PostMapping(value = "register")
    public R register(@RequestBody LoginUser user) {
        return apiUserService.register(user);
    }

    @Log("登出")
    @ApiOperation("登出")
    @PostMapping(value = "logout")
    public R logout() {
        // TODO 登出,删除redis和token
        return R.success();
    }

    @Log("账号密码登录")
    @ApiOperation("账号密码登录")
    @PostMapping(value = "loginByAccount")
    public R loginByAccount(@RequestBody LoginUser user) {
        return apiUserService.loginByAccount(user);
    }

    @Log("手机验证码登录")
    @ApiOperation("手机验证码登录")
    @PostMapping(value = "loginByPhoneCaptcha")
    public R loginBySms(@RequestBody LoginUser user) {
        return apiUserService.loginBySms(user);
    }

    @Log("微信一键登录")
    @ApiOperation("微信一键登录")
    @PostMapping(value = "loginByWx")
    public R loginByWx(@RequestBody LoginUser user) {
//        return apiUserService.loginByWx(user);
        return R.success();
    }

    @Log("支付宝一键登录")
    @ApiOperation("支付宝一键登录")
    @PostMapping(value = "loginByZfb")
    public R loginByZfb(@RequestBody LoginUser user) {
//        return apiUserService.loginByZfb(user);
        return R.success();
    }

    @Log("Token续期")
    @ApiOperation("Token续期")
    @PostMapping(value = "refToken")
    public R refToken(HttpServletResponse response) {
        // TODO Token续期
        return R.success();
    }

    @ApiOperation("获取图形验证码")
    @GetMapping(value = "code")
    public R getCode() {
        return apiUserService.getCode();
    }

    @ApiOperation("注销")
    @GetMapping(value = "deleted")
    public R deleted(Long userId) {
        return apiUserService.deleted(userId);
    }

    @Log("手机号注册获取短信验证码")
    @ApiOperation("手机号注册获取短信验证码")
    @GetMapping(value = "captchaByRegister/{phone}")
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
    @Log("忘记密码-发送短信验证码")
    @ApiOperation("忘记密码-发送短信验证码")
    @GetMapping("captchaByResetPassword/{phone}")
    public R resetCaptcha(@PathVariable String phone) {
        // 判断手机号是否正确，是否存在数据库
        if (!apiUserService.phoneExits(phone)) {
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

}
