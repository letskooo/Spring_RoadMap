package hello.login;

import hello.login.web.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        // 직접 만든 LogFilter를 넣어줌
        filterRegistrationBean.setFilter(new LogFilter());
        // 필터 체인 중에서 해당 필터의 순서 정하기
        filterRegistrationBean.setOrder(1);
        // 어떤 URL 패턴에 필터를 적용시킬거냐
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
