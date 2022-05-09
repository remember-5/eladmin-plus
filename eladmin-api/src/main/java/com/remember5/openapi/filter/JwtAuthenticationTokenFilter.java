package com.remember5.openapi.filter;

import cn.hutool.core.util.StrUtil;
import com.remember5.openapi.entity.ApiUserDetails;
import com.remember5.openapi.modules.apiuser.service.ApiUserService;
import com.remember5.openapi.modules.apiuser.service.dto.ApiUserDto;
import com.remember5.openapi.modules.apiuser.service.mapstruct.ApiUserMapper;
import com.remember5.openapi.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.properties.JwtProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
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
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private ApiUserService apiUserService;
    @Resource
    private ApiUserMapper apiUserMapper;
    @Resource
    private JwtTokenUtils jwtTokenUtils;
    @Resource
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        // 对于 Token 为空的不需要去查 Redis
        if (StrUtil.isNotBlank(token)) {
            String phone = jwtTokenUtils.getPhoneByToken(token);
            if (phone != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                ApiUserDto user = apiUserService.findByPhone(phone);
                ApiUserDetails userDetails = new ApiUserDetails(apiUserMapper.toEntity(user));
                if (Objects.nonNull(user)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
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