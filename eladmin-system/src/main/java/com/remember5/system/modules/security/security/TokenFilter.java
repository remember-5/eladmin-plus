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
package com.remember5.system.modules.security.security;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.jwt.JWTException;
import com.remember5.core.properties.JwtProperties;
import com.remember5.core.utils.TokenProvider;
import com.remember5.system.modules.security.service.OnlineUserService;
import com.remember5.system.modules.security.service.UserCacheClean;
import com.remember5.system.modules.security.service.dto.OnlineUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author /
 */
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final OnlineUserService onlineUserService;
    private final UserCacheClean userCacheClean;

    /**
     * @param tokenProvider     Token
     * @param jwtProperties     JWT
     * @param onlineUserService 用户在线
     * @param userCacheClean    用户缓存清理工具
     */
    public TokenFilter(TokenProvider tokenProvider, JwtProperties jwtProperties, OnlineUserService onlineUserService, UserCacheClean userCacheClean) {
        this.jwtProperties = jwtProperties;
        this.onlineUserService = onlineUserService;
        this.tokenProvider = tokenProvider;
        this.userCacheClean = userCacheClean;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = resolveToken(request);
        // 校验token
        if(Boolean.FALSE.equals(tokenProvider.verifyToken(token))) {
            log.error("token verify error!");
            filterChain.doFilter(request, response);
        }
        // 对于 Token 为空的不需要去查 Redis
        if (CharSequenceUtil.isNotBlank(token)) {
            OnlineUserDto onlineUserDto = null;
            boolean cleanUserCache = false;
            try {
                // 会去redis校验存不存在这个key
                onlineUserDto = onlineUserService.getOne(jwtProperties.getOnlineKey() + token);
            } catch (JWTException e) {
                log.error(e.getMessage());
                cleanUserCache = true;
            } finally {
                if (cleanUserCache || Objects.isNull(onlineUserDto)) {
                    userCacheClean.cleanUserCache(String.valueOf(tokenProvider.getClaims(token).getClaim(TokenProvider.AUTHORITIES_KEY)));
                }
            }
            if (onlineUserDto != null && StringUtils.hasText(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Token 续期
                tokenProvider.checkRenewal(token);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 初步检测Token
     *
     * @param request /
     * @return /
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getTokenStartWith())) {
            // 去掉令牌前缀
            return bearerToken.replace(jwtProperties.getTokenStartWith(), "");
        } else {
            log.debug("非法Token：{}", bearerToken);
        }
        return null;
    }


}
