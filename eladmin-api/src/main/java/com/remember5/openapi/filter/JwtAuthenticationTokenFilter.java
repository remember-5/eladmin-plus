package com.remember5.openapi.filter;

import cn.hutool.core.text.CharSequenceUtil;
import com.remember5.openapi.modules.apiuser.domain.ApiUser;
import com.remember5.openapi.modules.apiuser.repository.ApiUserRepository;
import com.remember5.redis.properties.JwtProperties;
import com.remember5.redis.utils.TokenProvider;
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
    private final ApiUserRepository apiUserRepository;

    /**
     * 拦截器
     *
     * @param request     /
     * @param response    /
     * @param filterChain /
     * @throws ServletException /
     * @throws IOException      /
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getTokenStartWith())) {
            // 去掉令牌前缀
            String token = bearerToken.replace(jwtProperties.getTokenStartWith(), "");
            if (CharSequenceUtil.isNotBlank(token)) {
                // 校验token
                if (!tokenProvider.verifyToken(token)) {
                    log.error("token verify error! : {}", bearerToken);
                    filterChain.doFilter(request, response);
                }

                String phone = tokenProvider.getSubject(token);
                if (phone != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    final ApiUser user = apiUserRepository.findByPhone(phone);
                    if (Objects.nonNull(user)) {
                        // todo @log注解获取不到username
                        Authentication authentication = tokenProvider.getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        log.error("token verify error! : {}", bearerToken);
        filterChain.doFilter(request, response);
    }

}
