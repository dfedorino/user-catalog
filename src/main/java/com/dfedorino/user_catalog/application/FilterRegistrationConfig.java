package com.dfedorino.user_catalog.application;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfig {

    @Bean
    public FilterRegistrationBean<CookieFilter> cookieValidatorFilter() {
        FilterRegistrationBean<CookieFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(cookieFilter());
        registrationBean.addUrlPatterns("*");
        return registrationBean;
    }

    @Bean
    public CookieFilter cookieFilter() {
        return new CookieFilter();
    }
}
