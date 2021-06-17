package com.dfedorino.user_catalog.controller;

import com.dfedorino.user_catalog.service.SecurityService;
import com.dfedorino.user_catalog.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginPassword loginPassword) {
        UserDetails userDetails = userService.loadUserByUsername(loginPassword.getLogin());
        //TODO: implement password check and solve issue with received hashed passwords and stored hashed passwords
        boolean isSuccessfulLogin = userDetails.getUsername().equals(loginPassword.getLogin());
        if (isSuccessfulLogin) {
            String token = securityService.generateJwt(loginPassword.getLogin());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CACHE_CONTROL, "no-store")
                    .header(HttpHeaders.PRAGMA, "no-cache")
                    .body("{" +
                            "\"access_token\":" + "\"" + token + "\"," +
                            "\"token_type\":" + "\"Bearer\"" +
                            "}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Data
    private static class LoginPassword {
        String login;
        String password;
    }
}
