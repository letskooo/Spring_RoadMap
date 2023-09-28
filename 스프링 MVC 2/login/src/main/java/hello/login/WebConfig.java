package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)   // 인터셉터 필터 중 순서 1번
                .addPathPatterns("/**") // 하위 전부다
                .excludePathPatterns("/css/**", "/*.ico", "/error");  // 여기는 인터셉터 적용하지마
    }

//    @Bean
//    public FilterRegistrationBean logFilter(){
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//        // 직접 만든 LogFilter를 넣어줌
//        filterRegistrationBean.setFilter(new LogFilter());
//        // 필터 체인 중에서 해당 필터의 순서 정하기
//        filterRegistrationBean.setOrder(1);
//        // 어떤 URL 패턴에 필터를 적용시킬거냐
//        filterRegistrationBean.addUrlPatterns("/*");
//
//        return filterRegistrationBean;
//    }

    @Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");    // 화이트 리스트를 적어놨기 때문에 전체로 해놔도 됨

        return filterRegistrationBean;
    }
}
