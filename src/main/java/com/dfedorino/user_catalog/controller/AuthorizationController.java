package com.dfedorino.user_catalog.controller;

import com.dfedorino.user_catalog.repository.exception.UserNotFoundException;
import com.dfedorino.user_catalog.service.SecurityService;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    @PostMapping("/auth")
    public void authenticate(@RequestBody ObjectNode json, HttpServletResponse response) throws IOException {
        String login = json.get("login").asText();
        String password = json.get("password").asText();
        if (service.isValidUserData(login, password)) {
            service.addJwtToResponse(response);
        } else {
            throw new UserNotFoundException(login);
        }
    }
}
