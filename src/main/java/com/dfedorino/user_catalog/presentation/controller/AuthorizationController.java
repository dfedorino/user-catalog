package com.dfedorino.user_catalog.presentation.controller;

import com.dfedorino.user_catalog.application.SecurityService;
import com.dfedorino.user_catalog.presentation.model.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthorizationController {
    private final SecurityService service;

    AuthorizationController(SecurityService service) {
        this.service = service;
    }

    @GetMapping("/auth")
    String loginPage() {
        return "Authentication page, please send login and password!";
    }

    @PostMapping("/auth")
    public void authenticate(@RequestBody ObjectNode json, HttpServletResponse response) throws IOException {
        String login = json.get("login").asText();
        String password = json.get("password").asText();
        if (service.isValidUserData(login, password)) {
            service.addAuthorizationCookie(response);
            response.sendRedirect("/users");
        } else {
            throw new UserNotFoundException(login);
        }
    }
}
