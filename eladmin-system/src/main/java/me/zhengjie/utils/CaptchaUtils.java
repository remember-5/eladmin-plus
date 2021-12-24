package me.zhengjie.utils;


import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import me.zhengjie.entity.CaptchaCode;
import me.zhengjie.entity.LoginProperties;
import me.zhengjie.exception.BadConfigurationException;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * @author wangjiahao
 * @date 2021/12/2
 */
@Component
@RequiredArgsConstructor
public class CaptchaUtils {

    private final LoginProperties loginProperties;

    /**
     * 获取验证码生产类
     *
     * @return /
     */
    public Captcha getCaptcha() {
        return switchCaptcha(loginProperties.getCaptchaCode());
    }

    /**
     * 依据配置信息生产验证码
     *
     * @param captchaCode 验证码配置信息
     * @return /
     */
    private Captcha switchCaptcha(CaptchaCode captchaCode) {
        Captcha captcha;
        synchronized (this) {
            switch (captchaCode.getType()) {
                case arithmetic:
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new FixedArithmeticCaptcha(captchaCode.getWidth(), captchaCode.getHeight());
                    // 几位数运算，默认是两位
                    captcha.setLen(captchaCode.getLength());
                    break;
                case chinese:
                    captcha = new ChineseCaptcha(captchaCode.getWidth(), captchaCode.getHeight());
                    captcha.setLen(captchaCode.getLength());
                    break;
                case chinese_gif:
                    captcha = new ChineseGifCaptcha(captchaCode.getWidth(), captchaCode.getHeight());
                    captcha.setLen(captchaCode.getLength());
                    break;
                case gif:
                    captcha = new GifCaptcha(captchaCode.getWidth(), captchaCode.getHeight());
                    captcha.setLen(captchaCode.getLength());
                    break;
                case spec:
                    captcha = new SpecCaptcha(captchaCode.getWidth(), captchaCode.getHeight());
                    captcha.setLen(captchaCode.getLength());
                    break;
                default:
                    throw new BadConfigurationException("验证码配置信息错误！正确配置查看 captchaCodeEnum ");
            }
        }
        if (StringUtils.isNotBlank(captchaCode.getFontName())) {
            captcha.setFont(new Font(captchaCode.getFontName(), Font.PLAIN, captchaCode.getFontSize()));
        }
        return captcha;
    }

    static class FixedArithmeticCaptcha extends ArithmeticCaptcha {
        public FixedArithmeticCaptcha(int width, int height) {
            super(width, height);
        }

        @Override
        protected char[] alphas() {
            // 生成随机数字和运算符
            int n1 = num(1, 10), n2 = num(1, 10);
            int opt = num(3);

            // 计算结果
            int res = new int[]{n1 + n2, n1 - n2, n1 * n2}[opt];
            // 转换为字符运算符
            char optChar = "+-x".charAt(opt);

            this.setArithmeticString(String.format("%s%c%s=?", n1, optChar, n2));
            this.chars = String.valueOf(res);

            return chars.toCharArray();
        }
    }
}
