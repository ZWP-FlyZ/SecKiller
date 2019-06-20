package com.zwp.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import security.*;

/**
 * @program: seckiller
 * @description: security 安全控制
 * @author: zwp-flyz
 * @create: 2019-06-20 13:37
 * @version: v1.0
 **/

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager mag = new InMemoryUserDetailsManager();
//        mag.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
//        return mag;
//    }


    @Bean
    public UserDetailsService accountProvider(){
        return new UserAccountProvider();
    }



//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication()
////                .withUser("user").password("{noop}password").roles("user");
//        super.configure(auth);
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     *  需要认证URL的相关配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginProcessingUrl("/login")
//                    .successForwardUrl("/login_succ")
//                    .failureForwardUrl("/login_fail")
                    .successHandler(new LogInSuccessHandler())
                    .failureHandler(new LogInFailHandler())
                    .permitAll()
                    .and()
                .httpBasic();

        // 登录与访问时异常处理
        http.exceptionHandling()
                .authenticationEntryPoint(new UnAuthorizedHandler())
                .accessDeniedHandler(new RequestAccessDeniedHandler());

    }


}
