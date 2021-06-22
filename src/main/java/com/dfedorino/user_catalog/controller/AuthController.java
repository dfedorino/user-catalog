package com.dfedorino.user_catalog.controller;

import com.dfedorino.user_catalog.security.CustomPasswordEncoder;
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
    @Autowired
    CustomPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginPassword loginPassword) {
        UserDetails userDetails = userService.loadUserByUsername(loginPassword.getLogin());
        String login = loginPassword.getLogin();
        String rawPassword = loginPassword.getPassword();
        String givenHashedPassword = new String(passwordEncoder.generateEncryptedSaltedBytes(login, rawPassword));
        String storedHashedPassword = userDetails.getPassword();
        boolean areMatchingPasswords = storedHashedPassword.equals(givenHashedPassword);
        if (areMatchingPasswords) {
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
    protected static class LoginPassword {
        String login;
        String password;
    }
}
