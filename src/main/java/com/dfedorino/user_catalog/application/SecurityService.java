package com.dfedorino.user_catalog.application;

import com.dfedorino.user_catalog.presentation.model.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class SecurityService {
    UserService userService;

    SecurityService(UserService userService) {
        this.userService = userService;
    }

    public boolean isValidJwt(String jwt) {
        return jwt.equals("JwtToken");
    }

    //TODO: seems like SRP is violated with this method
    public boolean isValidUserData(String login, String password) {
        User foundUser = userService.getUserByLogin(login);
        if (foundUser == null) {
            return false;
        }
        return foundUser.getPassword().equals(password);
    }

    public HttpServletResponse addAuthorizationCookie(HttpServletResponse response) {
        response.addCookie(new Cookie("Authorization", "JwtToken"));
        return response;
    }
}
