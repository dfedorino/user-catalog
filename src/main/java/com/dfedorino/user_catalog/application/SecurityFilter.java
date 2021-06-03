package com.dfedorino.user_catalog.application;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        System.out.println(">> Check request to " + request.getRequestURI());
        System.out.println(">> Request cookies:");
        Arrays.stream(request.getCookies()).forEach(cookie -> System.out.println(">>>> value -> " + cookie.getValue()));
        //call next filter in the filter chain
        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() { }
}
