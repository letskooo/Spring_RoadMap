package com.example.jwt.config;

import com.example.jwt.filter.MyFilter1;
import com.example.jwt.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter1> filter1(){

        FilterRegistrationBean<MyFilter1> filterRegistrationBean1 = new FilterRegistrationBean<>(new MyFilter1());
        filterRegistrationBean1.addUrlPatterns("/*");
        filterRegistrationBean1.setOrder(0); // 우선 순위가 가장 높음
        return filterRegistrationBean1;
    }
    @Bean
    public FilterRegistrationBean<MyFilter2> filter2(){

        FilterRegistrationBean<MyFilter2> filterRegistrationBean2 = new FilterRegistrationBean<>(new MyFilter2());
        filterRegistrationBean2.addUrlPatterns("/*");
        filterRegistrationBean2.setOrder(1); // 우선 순위가 가장 높음
        return filterRegistrationBean2;
    }
}
