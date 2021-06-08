package com.dfedorino.user_catalog.controller;

import com.dfedorino.user_catalog.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
        System.out.println(">> " + request.getMethod() + " request to " + request.getRequestURI() + " is being filtered...");
        String authValue = request.getHeader("Authorization");
        Cookie authCookie;
        if (authValue == null) {
            System.out.println(">> Authorization header was not found, redirect to /auth page");
            response.sendRedirect("/auth");
        } else {
            System.out.println(">> Authorization header found, validate JWT token...");
            boolean headerContainsValidToken = securityService.isValidToken(authValue);
            if (headerContainsValidToken) {
                System.out.println(">> Token is valid, let the request proceed to other filters...");
                filterChain.doFilter(request, response);
            } else {
                System.out.println(">> Token is invalid, redirect to /auth page");
                response.sendRedirect("/auth");
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return "/auth".equals(path);
    }

    private Cookie getAuthCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                return cookie;
            }
        }
        return null;
    }
}
