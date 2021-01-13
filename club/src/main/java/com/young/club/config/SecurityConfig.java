package com.young.club.config;

import com.young.club.security.filter.ApiCheckFilter;
import com.young.club.security.handler.ClubLoginSuccessHandler;
import com.young.club.security.service.ClubUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 에너테이션 기반의 접근 제한 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClubUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/sample/all").permitAll()
//                .antMatchers("/sample/user").hasRole("USER");

        http.formLogin(); // 인가/인증에 문제시 로그인 화면
        http.csrf().disable();
        http.logout();
        http.oauth2Login().successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60 * 60).userDetailsService(userDetailsService);
    }

    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/notes/**/*");
    }

    // AuthenticationManager 설정을 쉽게 처리할 수 있도록 해줌
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 테스트용 user1 사용자 계정
//        auth.inMemoryAuthentication().withUser("user1")
//                .password("$2a$10$OkdzaA26GaeubcA6E7RV2.2GNkJQEA9C2wyWW4Fcq/xl5NzObz/qW")
//                .roles("USER");
//    }
}
