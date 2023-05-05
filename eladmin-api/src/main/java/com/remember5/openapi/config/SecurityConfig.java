package com.remember5.openapi.config;

import com.remember5.security.handler.JwtAccessDeniedHandler;
import com.remember5.security.handler.JwtAuthenticationEntryPoint;
import com.remember5.openapi.filter.JwtAuthenticationTokenFilter;
import com.remember5.openapi.modules.apiuser.repository.ApiUserRepository;
import com.remember5.redis.properties.JwtProperties;
import com.remember5.redis.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity的配置
 *
 * <p>
 * configure(HttpSecurity httpSecurity)：用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器；
 * configure(AuthenticationManagerBuilder auth)：用于配置UserDetailsService及PasswordEncoder；
 * RestfulAccessDeniedHandler：当用户没有访问权限时的处理器，用于返回JSON格式的处理结果；
 * RestAuthenticationEntryPoint：当未登录或token失效时，返回JSON格式的结果；
 * UserDetailsService:SpringSecurity定义的核心接口，用于根据用户名获取用户信息，需要自行实现；
 * UserDetails：SpringSecurity定义用于封装用户信息的类（主要是用户信息和权限），需要自行实现；
 * PasswordEncoder：SpringSecurity定义的用于对密码进行编码及比对的接口，目前使用的是BCryptPasswordEncoder；
 * JwtAuthenticationTokenFilter：在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录。
 * </p>
 *
 * @author wangjiahao
 * @date 2021/4/1
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProperties jwtProperties;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationTokenFilter;
    private final ApiUserRepository apiUserRepository;
    private final TokenProvider tokenProvider;

    /**
     * 密码加密方式
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 添加JWT filter
                .addFilterBefore(new JwtAuthenticationTokenFilter(jwtProperties, tokenProvider, apiUserRepository), UsernamePasswordAuthenticationFilter.class)
                // 授权异常 添加自定义未授权和未登录结果返回
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationTokenFilter)
                .and()
                // 防止iframe 造成跨域
                .headers()
                // 禁用缓存
                .cacheControl()
                .and()
                .frameOptions()
                .disable()
                // 不创建会话 基于token，所以不需要session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(jwtProperties.getPermitUrl().toArray(new String[0])).permitAll()
                // 放行OPTIONS请求,跨域请求会先进行一次options请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
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
        web.ignoring().antMatchers(jwtProperties.getPermitUrl().toArray(new String[0]));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
