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
package com.remember5.security.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.RegisteredPayload;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.remember5.security.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


/**
 * token生成工具类
 * <p>双token的刷新 access_token和refresh_token</p>
 * 第一次用账号密码登录服务器会返回两个 token:
 * access_token 和 refresh_token，时效长短不一样。短的access_token 时效过了之后，发送时效长的 refresh_token 重新获取一个短时效token，如果都过期，就需要重新登录了。
 * refresh_token 就是用来刷新access_token 。活跃用户的 access_token 过期了，用refresh_token 获取 新的access_token 。
 * <p>
 * <p>access_token 和 refresh_token 的有效时间如何设置:</p>
 * <p>为了保证能够刷新活跃用户的access_token ， refresh_token 的有效时间 不能小于 用户活跃时间点</p>
 * <p>假设 access_token 有效时间是 et ，那么用户在 [ access_token 起始时间点 ，2*et ] 时间内用户是活跃的 ，由此可知 refresh_token 的有效时间 >= 2 * access_token 的有效时间</p>
 * <p>一般，refresh_token 的有效时间 > 2 * access_token 的有效时间</p>
 * <p>比如，access_token 实效7天，那么 refresh_token 实效可以给15天,也可以给30天</p>
 * <p>当然，access_token和refresh_token 的时长具体多少，需要根据环境决定，如涉及到金钱的 银行客户端，12306客户端等 token时长都很短，而普通app客户端的token可以是几天甚至上月.</p>
 * <p>刷新refresh_token:</p>
 * 每次 刷新access_token 时判断 refresh_token 是否快过期，如果是，那就连refresh_token 也刷新。
 * 如果希望降低 刷新refresh_token 频率，可以将 refresh_token 实效提高
 *
 * @author wangjiahao
 */
@Slf4j
public class TokenProvider implements InitializingBean {

    private final JwtProperties jwtProperties;
    public static final String AUTHORITIES_KEY = "username";

    private JWT jwt;

    public TokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public void afterPropertiesSet() {
        JWTSigner signer = JWTSignerUtil.hs512(jwtProperties.getBase64Secret().getBytes(StandardCharsets.UTF_8));
        jwt = JWT.create().setSigner(signer);
    }

    /**
     * 创建一个会过期的access_token
     *
     * @param userId   用户id
     * @param username 用户名称
     * @return /
     */
    public String createAccessToken(Long userId, String username) {
        return createAccessToken(userId, username, null);
    }

    public String createAccessToken(Long userId, String username, String phone) {
        jwt.setJWTId(IdUtil.simpleUUID())
            .setSubject(String.valueOf(userId))
            .setPayload(AUTHORITIES_KEY, username)
            .setIssuer("admin")
            .setNotBefore(new Date())
            .setIssuedAt(new Date())
            .setExpiresAt(DateUtil.offset(new Date(), DateField.SECOND, Math.toIntExact(jwtProperties.getTokenValidityInSeconds())));
        if (phone != null) {
            jwt.setPayload("phone", phone);
        }
        return jwt.sign();
    }

    /**
     * 创建一个会过期的refresh_token
     *
     * @param userId   用户id
     * @param username 用户名称
     * @return /
     */
    public String createRefreshToken(String userId, String username) {
        return jwt
                // 加入ID确保生成的 Token 都不一致
                .setJWTId(IdUtil.simpleUUID())
                .setSubject(userId)
                .setPayload(AUTHORITIES_KEY, username)
                .setIssuer("admin")
                .setNotBefore(new Date())
                .setIssuedAt(new Date())
                .setExpiresAt(DateUtil.offset(new Date(), DateField.SECOND, Math.toIntExact(jwtProperties.getTokenValidityInSeconds() * 3)))
                .sign();
    }

    /**
     * 校验token是否被串改或过期
     *
     * @param token token
     * @return 校验结果
     */
    public boolean verifyToken(String token) {
        return JWT.of(token).setKey(jwtProperties.getBase64Secret().getBytes()).validate(0);
    }

    /**
     * 获取token解密的json串
     *
     * @param token token
     * @return json token
     */
    public JWTPayload getClaims(String token) {
        return jwt.parse(token).getPayload();
    }

    /**
     * 获取token中的sub
     *
     * @param token token
     * @return sub
     */
    public String getSubject(String token) {
        return jwt.parse(token).getPayload(RegisteredPayload.SUBJECT).toString();
    }

    /**
     * 获取token中的用户名
     *
     * @param token token
     * @return 用户名
     */
    public String getUsername(String token) {
        return jwt.parse(token).getPayload(AUTHORITIES_KEY).toString();
    }

    /**
     * 获取token中的用户名
     *
     * @param token token
     * @return 用户名
     */
    public Integer getExpires(String token) {
        return Integer.parseInt(jwt.parse(token).getPayload(RegisteredPayload.EXPIRES_AT).toString());
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

    public Integer getUserId() {
        String token = getTokenByRequest();
        String userId = jwt.parse(token).getPayload("sub").toString();
        return Integer.parseInt(userId);
    }

    public String getUsername() {
        String token = getTokenByRequest();
        return getUsername(token);
    }


    /**
     * @param token 需要检查的token
     */
    public void checkRenewal(String token) {
        final Integer expires = getExpires(token);
        if (Math.floorDiv(System.currentTimeMillis(), 1000) + jwtProperties.getDetect() > expires) {
        }
        // todo 双token续期
    }


    public String getTokenByRequest() {
        final HttpServletRequest request = getHttpServletRequest();
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(jwtProperties.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static String getHeader(String name) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) getHttpServletRequest();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader(name);
    }
}
