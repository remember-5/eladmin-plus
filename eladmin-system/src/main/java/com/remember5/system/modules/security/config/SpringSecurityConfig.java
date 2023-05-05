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
package com.remember5.system.modules.security.config;

import com.remember5.core.annotation.AnonymousAccess;
import com.remember5.core.annotation.rest.*;
import com.remember5.core.enums.RequestMethodEnum;
import com.remember5.security.handler.JwtAccessDeniedHandler;
import com.remember5.security.handler.JwtAuthenticationEntryPoint;
import com.remember5.redis.properties.JwtProperties;
import com.remember5.redis.utils.TokenProvider;
import com.remember5.system.modules.security.security.TokenFilter;
import com.remember5.system.modules.security.service.OnlineUserService;
import com.remember5.system.modules.security.service.UserCacheClean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author Zheng Jie
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    //        private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext applicationContext;
    private final JwtProperties jwtProperties;
    private final OnlineUserService onlineUserService;
    private final UserCacheClean userCacheClean;

    @Value("${spring.profiles.active}")
    private String env;

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // 去除 ROLE_ 前缀
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密方式
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 搜寻匿名标记 url： @AnonymousAccess
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // 获取匿名标记
        Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);
        httpSecurity
                // 禁用 CSRF
                .csrf().disable()
                // 添加拦截器
                .addFilterBefore(new TokenFilter(jwtProperties, tokenProvider, onlineUserService, userCacheClean), UsernamePasswordAuthenticationFilter.class)
                // 授权异常
                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // 防止iframe 造成跨域
                .and()
                .headers()
                .frameOptions()
                .disable()
                // 不创建会话
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(jwtProperties.getPermitUrl().toArray(new String[0])).permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 自定义匿名访问所有url放行：允许匿名和带Token访问，细腻化到每个 Request 类型
                // GET
                .antMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0])).permitAll()
                // POST
                .antMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0])).permitAll()
                // PUT
                .antMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethodEnum.PUT.getType()).toArray(new String[0])).permitAll()
                // PATCH
                .antMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0])).permitAll()
                // DELETE
                .antMatchers(HttpMethod.DELETE, anonymousUrls.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0])).permitAll()
                // 所有类型的接口都放行
                .antMatchers(anonymousUrls.get(RequestMethodEnum.ALL.getType()).toArray(new String[0])).permitAll()
                // 所有请求都需要认证
                .anyRequest().authenticated();
    }

    /**
     * 虽然这两个都是继承WebSecurityConfigurerAdapter后重写的方法，
     * 但是http.permitAll不会绕开springsecurity的过滤器验证，相当于只是允许该路径通过过滤器
     * 而web.ignoring是直接绕开spring security的所有filter，直接跳过验证。
     *
     * @param web /
     * @throws Exception /
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 获取匿名标记
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);
//        web.ignoring().antMatchers(jwtProperties.getPermit().getUrl().toArray(new String[0]));
        web.ignoring().antMatchers(anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0]));
        web.ignoring().antMatchers(anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0]));
        web.ignoring().antMatchers(anonymousUrls.get(RequestMethodEnum.PUT.getType()).toArray(new String[0]));
        web.ignoring().antMatchers(anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0]));
        web.ignoring().antMatchers(anonymousUrls.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0]));
        web.ignoring().antMatchers(anonymousUrls.get(RequestMethodEnum.ALL.getType()).toArray(new String[0]));
    }

    private Map<String, Set<String>> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        Map<String, Set<String>> anonymousUrls = new HashMap<>(6);
        Set<String> get = new HashSet<>();
        Set<String> post = new HashSet<>();
        Set<String> put = new HashSet<>();
        Set<String> patch = new HashSet<>();
        Set<String> delete = new HashSet<>();
        Set<String> all = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                List<RequestMethod> requestMethods = new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
                RequestMethodEnum request = RequestMethodEnum.find(requestMethods.isEmpty() ? RequestMethodEnum.ALL.getType() : requestMethods.get(0).name());
                switch (Objects.requireNonNull(request)) {
                    case GET:
                        AnonymousGetMapping anonymousGetMapping = infoEntry.getValue().getMethodAnnotation(AnonymousGetMapping.class);
                        if (isRejected(anonymousGetMapping.rejectedEnvs())) {
                            break;
                        }
                        get.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case POST:
                        AnonymousPostMapping anonymousPostMapping = infoEntry.getValue().getMethodAnnotation(AnonymousPostMapping.class);
                        if (isRejected(anonymousPostMapping.rejectedEnvs())) {
                            break;
                        }
                        post.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case PUT:
                        AnonymousPutMapping anonymousPutMapping = infoEntry.getValue().getMethodAnnotation(AnonymousPutMapping.class);
                        if (isRejected(anonymousPutMapping.rejectedEnvs())) {
                            break;
                        }
                        put.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case PATCH:
                        AnonymousPatchMapping anonymousPatchMapping = infoEntry.getValue().getMethodAnnotation(AnonymousPatchMapping.class);
                        if (isRejected(anonymousPatchMapping.rejectedEnvs())) {
                            break;
                        }
                        patch.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case DELETE:
                        AnonymousDeleteMapping anonymousDeleteMapping = infoEntry.getValue().getMethodAnnotation(AnonymousDeleteMapping.class);
                        if (isRejected(anonymousDeleteMapping.rejectedEnvs())) {
                            break;
                        }
                        delete.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    default:
                        all.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                }
            }
        }
        anonymousUrls.put(RequestMethodEnum.GET.getType(), get);
        anonymousUrls.put(RequestMethodEnum.POST.getType(), post);
        anonymousUrls.put(RequestMethodEnum.PUT.getType(), put);
        anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch);
        anonymousUrls.put(RequestMethodEnum.DELETE.getType(), delete);
        anonymousUrls.put(RequestMethodEnum.ALL.getType(), all);
        return anonymousUrls;
    }

    /**
     * 当前环境是否在拒绝匿名访问列表中
     * @param rejectedEnvs 拒绝匿名访问列表
     * @return t 是 f 否
     */
    private boolean isRejected(String[] rejectedEnvs){
        for (String rejectedEnv : rejectedEnvs) {
            if (env.equals(rejectedEnv)) {
                return true;
            }
        }
        return false;
    }


}
