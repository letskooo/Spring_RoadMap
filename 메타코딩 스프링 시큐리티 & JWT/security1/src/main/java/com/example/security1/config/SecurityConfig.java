package com.example.security1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화. 스프링 시큐리티 필터가 스프링 필터체인에 등록됨.
@Slf4j
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        log.info("----------configure--------------");

        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
                        .requestMatchers("/manager/**").hasAnyRole("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
                        .requestMatchers("/admin/**").hasAnyRole("hasRole('ROLE_ADMIN')")
                        .anyRequest().permitAll());
        http.formLogin(requests ->
                requests.loginPage("/loginForm")    // 로그인 페이지를 불러올 때는 이 메소드를 호출
                        .loginProcessingUrl("/login")   // 시큐리티가 /login 요청이 오면 낚아챔.
                        .defaultSuccessUrl("/"));   // 로그인이 성공하면 다음 url로 리다이렉트

        return http.build();

    }
}
