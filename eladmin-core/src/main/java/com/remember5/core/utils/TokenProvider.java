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
package com.remember5.core.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.remember5.core.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author /
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private final JwtProperties jwtProperties;
    private final RedisUtils redisUtils;
    public static final String AUTHORITIES_KEY = "user";

    private JWT jwt;

    public TokenProvider(JwtProperties jwtProperties, RedisUtils redisUtils) {
        this.jwtProperties = jwtProperties;
        this.redisUtils = redisUtils;
    }

    @Override
    public void afterPropertiesSet() {
        JWTSigner signer = JWTSignerUtil.hs512(jwtProperties.getBase64Secret().getBytes(StandardCharsets.UTF_8));
        jwt = JWT.create().setSigner(signer);
    }

    /**
     * 创建Token 设置永不过期，
     * Token 的时间有效性转到Redis 维护
     *
     * @param authentication /
     * @return token
     */
    public String createToken(Authentication authentication) {
        return createToken(authentication.getName(), authentication.getName());
    }


    public String createToken(String sub, String username) {
        return jwt
                // 加入ID确保生成的 Token 都不一致
                .setJWTId(IdUtil.simpleUUID())
                .setSubject(sub)
                .setPayload(AUTHORITIES_KEY, username)
                .sign();
    }

    /**
     * 依据Token 获取鉴权信息
     *
     * @param token /
     * @return /
     */
    public Authentication getAuthentication(String token) {
        JWTPayload claims = getClaims(token);
        User principal = new User(claims.getClaim("sub").toString(), "******", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    public JWTPayload getClaims(String token) {
        return jwt.parse(token).getPayload();
    }

    public String getSubject(String token) {
        return jwt.parse(token).getPayload("sub").toString();
    }

    /**
     * @param token 需要检查的token
     */
    public void checkRenewal(String token) {
        // 判断是否续期token,计算token的过期时间
        long time = redisUtils.getExpire(jwtProperties.getOnlineKey() + token) * 1000;
        Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
        // 判断当前时间与过期时间的时间差
        long differ = expireDate.getTime() - System.currentTimeMillis();
        // 如果在续期检查的范围内，则续期
        if (differ <= jwtProperties.getDetect()) {
            long renew = time + jwtProperties.getRenew();
            redisUtils.expire(jwtProperties.getOnlineKey() + token, renew, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 检查token是否过期
     *
     * @param token 需要检查的token
     */
    public Boolean checkToken(String token) {
        // 判断是否续期token,计算token的过期时间
        long time = redisUtils.getExpire(jwtProperties.getOnlineKey() + token) * 1000;
        Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
        // 判断当前时间与过期时间的时间差
        return expireDate.getTime() > System.currentTimeMillis();
    }

    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(jwtProperties.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }


    /**
     * 校验token
     *
     * @param token
     * @return
     */
    public Boolean verifyToken(String token) {
        return JWTUtil.verify(token, jwtProperties.getBase64Secret().getBytes());
    }


    public static void main(String[] args) {
        String key = "ZepaCb507jDke9PwQRk6tomQhktLUc9TMtCtAuCw4nrcymq3BHQdMBUV";
        JWTSigner signer = JWTSignerUtil.hs512(key.getBytes(StandardCharsets.UTF_8));
        JWT myJwt = JWT.create().setSigner(signer);

        String sign = myJwt
                // 加入ID确保生成的 Token 都不一致
                .setJWTId(IdUtil.simpleUUID())
                .setSubject("setSubject")
                .setPayload(AUTHORITIES_KEY, "setPayload")
                .sign();

        System.err.println(myJwt.parse(sign).getPayload());
        System.err.println(myJwt.parse(sign + "1").getPayload());


        System.err.println(JWTUtil.verify(sign, key.getBytes()));
        System.err.println(JWTUtil.verify(sign + "1", key.getBytes()));
    }


}
