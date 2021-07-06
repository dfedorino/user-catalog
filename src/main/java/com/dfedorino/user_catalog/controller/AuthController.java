package com.dfedorino.user_catalog.controller;

import com.dfedorino.user_catalog.repository.User;
import com.dfedorino.user_catalog.security.CustomPasswordEncoder;
import com.dfedorino.user_catalog.service.SecurityService;
import com.dfedorino.user_catalog.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    SecurityService securityService;
    @Autowired
    CustomPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginPassword loginPassword) {
        User user = userService.getUserByLogin(loginPassword.getLogin());
        System.out.println(">> user found -> " + user);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String rawPassword = loginPassword.getPassword();
        String salt = user.getSalt();
        String hashedGivenPassword = passwordEncoder.generateEncryptedSaltedBytes(salt, rawPassword);
        String givenHashedPassword = new String(hashedGivenPassword);
        String storedHashedPassword = user.getPassword();
        boolean areMatchingPasswords = storedHashedPassword.equals(givenHashedPassword);
        System.out.println(">> passwords are matching -> " + areMatchingPasswords);
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
