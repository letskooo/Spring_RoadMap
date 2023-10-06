package com.example.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    // 스프링이 들고 있는 Cors 필터
    @Bean
    public CorsFilter corsFilter(){

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);   // 내 서버가 응답할 때 JSON 을 자바스크립트에서 처리할 수 있는지의 여부를 설정
        config.addAllowedOrigin("*");       // 모든 IP에 대한 응답을 허용
        config.addAllowedHeader("*");       // 모든 header 에 응답을 허용
        config.addAllowedMethod("*");       // 모든 get, post, put, delete, patch 요청을 허용
        source.registerCorsConfiguration("/api/**", config); // 해당되는 모든 주소는 config 설정을 따르라는 의미

        return new CorsFilter(source);
    }
}
