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
import com.remember5.redis.properties.JwtProperties;
import com.remember5.redis.utils.TokenProvider;
import com.remember5.system.modules.security.service.OnlineUserService;
import com.remember5.system.modules.security.service.UserCacheClean;
import com.remember5.system.modules.security.service.dto.OnlineUserDto;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final TokenProvider tokenProvider;
    private final OnlineUserService onlineUserService;
    private final UserCacheClean userCacheClean;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getTokenStartWith())) {
            // 去掉令牌前缀
            String token = bearerToken.replace(jwtProperties.getTokenStartWith(), "");
            // 对于 Token 为空的不需要去查 Redis
            if (CharSequenceUtil.isNotBlank(token)) {
                // 校验token
                if (!tokenProvider.verifyToken(token)) {
                    log.error("token verify error! : {}", token);
                    filterChain.doFilter(request, response);
                }
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
        } else {
            log.error("token verify error! : {}", bearerToken);
        }

        filterChain.doFilter(request, response);
    }


}
