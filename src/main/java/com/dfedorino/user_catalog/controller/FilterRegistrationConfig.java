package com.dfedorino.user_catalog.controller;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfig {

    @Bean
    public FilterRegistrationBean<HeaderFilter> cookieValidatorFilter() {
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(headerFilter());
        registrationBean.addUrlPatterns("*");
        return registrationBean;
    }

    @Bean
    public HeaderFilter headerFilter() {
        return new HeaderFilter();
    }
}
