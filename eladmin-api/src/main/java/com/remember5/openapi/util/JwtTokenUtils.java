package com.remember5.openapi.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.remember5.openapi.modules.apiuser.service.dto.ApiUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.properties.JwtProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * jwt 工具类
 * 用hutool提供的工具替换
 *
 * @author wangjiahao
 * @date 2021/4/1
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    private final JwtProperties jwtConfig;

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtConfig.getTokenValidityInSeconds() * 1000);
    }

    /**
     * 判断Token的Secret和是否已经失效
     */
    public boolean isTokenExpired(String token) {
        try {
            JWTValidator.of(token)
                    .validateAlgorithm(JWTSignerUtil.hs512(jwtConfig.getBase64Secret().getBytes()))
                    .validateDate(DateUtil.date());
            return true;
        } catch (ValidateException e) {
            log.error("jwt 解析异常 {} token: {}", e.getMessage(), token);
            return false;
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 生成jwt token
     *
     * @param apiUser /
     * @return token
     */
    public String generateToken(ApiUserDto apiUser) {
        return JWT.create()
                .setSigner(JWTSignerUtil.hs512(jwtConfig.getBase64Secret().getBytes()))
                .setPayload("sub", "1234567890")
                .setPayload("id", apiUser.getId())
                .setPayload("phone", apiUser.getPhone())
                .setPayload("username", apiUser.getUsername())
                .setExpiresAt(generateExpirationDate())
                .sign();
    }

    /**
     * 根据token获取手机号
     *
     * @return
     */
    public String getPhoneByToken() {
        final String token = getTokenByRequest();
        return getPhoneByToken(token);
    }

    /**
     * 根据token获取手机号
     *
     * @return
     */
    public String getPhoneByToken(String token) {
        if (isTokenExpired(token)) {
            final JWT jwt = JWTUtil.parseToken(token);
            jwt.getHeader(JWTHeader.TYPE);
            return jwt.getPayload("phone").toString();
        }
        return null;
    }

    /**
     * 在httpRequest中获取jwt token
     *
     * @return jwt token
     */
    public String getTokenByRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String bearerToken = request.getHeader(jwtConfig.getHeader());
        return bearerToken.replace(jwtConfig.getTokenStartWith(), "");
    }

}
