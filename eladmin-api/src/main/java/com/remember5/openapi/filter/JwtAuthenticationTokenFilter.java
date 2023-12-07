package com.remember5.openapi.filter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONException;
import cn.hutool.jwt.JWTException;
import com.remember5.security.properties.JwtProperties;
import com.remember5.security.utils.TokenProvider;
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

/**
 * JWT登录授权过滤器
 *
 * @author wangjiahao
 * @date 2021/4/1
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader(jwtProperties.getHeader());
            // 1. 不存在token 或 token不以Bearer开头，则直接放行
            if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(jwtProperties.getTokenStartWith())) {
                filterChain.doFilter(request, response);
            } else {
                // 2. 去掉令牌前缀
                String token = bearerToken.replace(jwtProperties.getTokenStartWith(), "");
                // 3. 校验token
                if (CharSequenceUtil.isBlank(token) || !tokenProvider.verifyToken(token)) {
                    filterChain.doFilter(request, response);
                } else {
                    // 4. 设置认证信息,如果不配置authentication 则会进入AuthenticationException ，转而进入JwtAuthenticationEntryPoint
                    Authentication authentication = tokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                }
            }
        } catch (JSONException | JWTException e) {
            log.error("token校验失败", e);
            filterChain.doFilter(request, response);
        }
    }


}
