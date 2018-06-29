package com.example.demo.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean someFilterRegistration1() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我们写好的过滤器
        registration.setFilter(new MyFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        //设置优先级
        registration.setOrder(1);
        return registration;
    }

}