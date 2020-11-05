//spring security 配置



package com.example.demo.config;


import com.example.demo.config.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    @Autowired
    private JwtAuthenticationFailHandler jwtAuthenticationFailHandler;
    @Autowired
    private UserInforDetailService userInforDetailService;

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(HttpMethod.GET,
//                "/dataImage/**/video/*.mp4");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors() //跨域设置
            .and()
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 关闭防跨站请求 禁用session
            .and()
                .formLogin()
                .usernameParameter("account")
                .passwordParameter("password")
                .successHandler(jwtAuthenticationSuccessHandler)  //登录成功后执行jwtAuthenticationSuccessHandler
                .failureHandler(jwtAuthenticationFailHandler)     //登录失败后执行jwtAuthenticationFailHandler
            .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/login").permitAll()   // login页面不需要认证就可以进入
                .antMatchers("/myLogin").permitAll()
                .antMatchers("/dataImage").permitAll()
                .antMatchers("/live").permitAll()
                .antMatchers("/data/**").access("@rbacService.hasPermission(request,authentication)");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userInforDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
