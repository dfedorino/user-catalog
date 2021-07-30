package com.dfedorino.user_catalog.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final Algorithm algorithm = Algorithm.HMAC256("secret");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        try {
//            Map<String, Claim> claims;
//            if (!hasJwtToken(request) || (claims = verifyTokenAndGetClaims(request)) == null) {
//                SecurityContextHolder.clearContext();
//            } else {
//                String username = claims.get("sub").asString();
//                // throws exception if doesn't exist
//                boolean isExistingUser = userService.getUserByLogin(username) != null;
//                setUpSpringAuthentication(claims);
//            }
//            chain.doFilter(request, response);
//        } catch (JWTVerificationException | UserNotFoundException e) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
//        }

        if (hasJwtToken(request)) {
            try {
                System.out.println(">> jwt found, verify token");
                Map<String, Claim> claims = verifyToken(request);
                if (claims == null) {
                    SecurityContextHolder.clearContext();
                    System.out.println(">> failed to read claims");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                    System.out.println(">> token verified, set up authentication");
                    setUpSpringAuthentication(claims);
                    chain.doFilter(request, response);
                }
            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            }
        } else {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
        }
    }

    private Map<String, Claim> verifyToken(HttpServletRequest request) throws JWTVerificationException {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        String issuer = "dfedorino.com";
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt = verifier.verify(jwtToken);
        return jwt.getClaims();
    }

    /**
     * Authentication method in Spring flow
     */
    private void setUpSpringAuthentication(Map<String, Claim> claims) {
        List<String> authorities = claims.get("authorities").asList(String.class);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.get("subject"), null,
                authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean hasJwtToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String prefix = "Bearer ";
        return authenticationHeader != null && authenticationHeader.startsWith(prefix);
    }

}