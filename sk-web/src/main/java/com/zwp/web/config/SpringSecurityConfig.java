package com.zwp.web.config;

import com.zwp.web.security.LogInFailHandler;
import com.zwp.web.security.RequestAccessDeniedHandler;
import com.zwp.web.security.UnAuthorizedHandler;
import com.zwp.web.security.UserAccountProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @program: seckiller
 * @description: security 安全控制
 * @author: zwp-flyz
 * @create: 2019-06-20 13:37
 * @version: v1.0
 **/

@Configuration
//@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager mag = new InMemoryUserDetailsManager();
//        mag.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
//        return mag;
//    }




//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }

    @Bean
    public UserDetailsService accountProvider(){
        return new UserAccountProvider();
    }


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
                    .antMatchers("/reg/**").permitAll()
                    .anyRequest().authenticated()
                    .and()


                .formLogin()
                    .loginProcessingUrl("/login")
                    .successForwardUrl("/login")
//                    .failureForwardUrl("/login_fail")
//                    .successHandler(new LogInSuccessHandler())
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
