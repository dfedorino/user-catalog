package com.dfedorino.user_catalog.controller;

import com.dfedorino.user_catalog.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class HeaderFilter extends OncePerRequestFilter {
    @Autowired
    SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        boolean isAuthenticated = securityService.isValidToken(request.getHeader("Authorization"));
        if (isAuthenticated) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.addHeader("WWW-Authenticate", "Bearer");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return  request.getRequestURI().equals("/users") &
                request.getMethod().equalsIgnoreCase("POST");
    }
}
