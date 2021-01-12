package com.young.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/user").hasRole("USER");

        http.formLogin(); // 인가/인증에 문제시 로그인 화면
        http.csrf().disable();
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
