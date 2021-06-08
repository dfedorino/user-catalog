package com.dfedorino.user_catalog.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.dfedorino.user_catalog.repository.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class SecurityService {
    UserService userService;
    Algorithm algorithm = Algorithm.HMAC256("secret");
    String issuer = "dfedorino.com";

    SecurityService(UserService userService) {
        this.userService = userService;
    }

    public boolean isValidToken(String authToken) {
        if (authToken == null) {
            return false;
        }
        String jwt = authToken.split(" ")[1];
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            verifier.verify(jwt);
        } catch (JWTVerificationException exception) {
            return false;
        }
        return true;
    }

    //TODO: seems like SRP is violated with this method
    public boolean isValidUserData(String login, String password) {
        User foundUser = userService.getUserByLogin(login);
        if (foundUser == null) {
            return false;
        }
        return foundUser.getPassword().equals(password);
    }

    public HttpServletResponse addJwtToResponse(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Pragma", "no-cache");
        String jwt = generateJwt();
        response.getWriter().write(
                "{" +
                    "\"access_token\":" + "\"" + jwt + "\"," +
                    "\"token_type\":" + "\"Bearer\"" +
                "}");
        return response;
    }

    private String generateJwt() {
        return JWT.create()
                .withIssuer(issuer)
                .sign(algorithm);
    }
}
