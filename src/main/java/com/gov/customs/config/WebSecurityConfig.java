package com.gov.customs.config;

import com.gov.customs.jwt.JwtAuthenticationFilter;
import com.gov.customs.jwt.JwtAuthenticationTokenFilter;
import com.gov.customs.jwt.JwtLoginFilter;
import com.gov.customs.security.CustomAccessDeniedHandler;
import com.gov.customs.security.CustomAuthenticationProvider;
import com.gov.customs.security.CustomUserDetailsService;
import com.gov.customs.security.EntryPointUnauthorizedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

import javax.annotation.Resource;

/**
 *
 * 跨域资源共享(CORS) 是一种机制，它使用额外的 HTTP 头来告诉浏览器  让运行在一个 origin (domain) 上的Web应用被准许访问来自不同源服务器上的指定的资源。
 * 当一个资源从与该资源本身所在的服务器不同的域、协议或端口请求一个资源时，资源会发起一个跨域 HTTP 请求。
 *
 * CSRF跨站点请求伪造(Cross—Site Request Forgery)，跟XSS攻击一样，存在巨大的危害性
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)// 控制权限注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Resource
    private CustomAuthenticationProvider customAuthenticationProvider;

//    @Resource
//    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Resource
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors()
                .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/dba/**").hasRole("DBA")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/","/register","/login","/index" ,"/signup","/signin","/forget",
                            "/isUsernameRegisted","/isIdentityRegisted","/isPhoneRegisted",
                            "/about", "/news", "/announce", "/online", "/topic",
                            "/junk/find","/news/list", "/announce/list", "/topic/list","/topic/find", "/reply/list", "/reply/find"
                            )
                    .permitAll()
                    .anyRequest().authenticated()//所有请求必须登陆后访问
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .successForwardUrl("/")
                    .failureUrl("/login-error")
                    .permitAll()//登录界面，错误界面可以直接访问
                .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll()//注销请求可直接访问
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(entryPointUnauthorizedHandler)
                    .accessDeniedHandler(customAccessDeniedHandler);
//                .and()
//                    .addFilter(new JwtLoginFilter(authenticationManager()))
//                    .addFilter(new JwtAuthenticationFilter(authenticationManager()));
//        //基于token，所以不需要session
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // 添加JWT filter
//        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//
//        // 禁用缓存
//        http.headers().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.GET,
                "/META-INF/resources/**",
                "/static/**",
                "/images/**",
                "/js/**",
                "/css/**",
                "/jquery/**",
                "/popper/**",
                "/bootstrap/**",
                "/bootstrap-table/**",
                "/font-awesome-4.7.0/**"
        );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        在用户注册的时候将用户密码加密, 验证的时候使用默认配置进行登录验证, 数据库中用户密码为密文
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);

//        在用户注册的时候不将用户密码加密, 验证的时候使用自定义类 CustomAuthenticationProvider 验证, 数据库中用户密码为明文
        auth.authenticationProvider(customAuthenticationProvider);
    }
}
