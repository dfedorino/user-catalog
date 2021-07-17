package com.dfedorino.user_catalog.controller;

import com.dfedorino.user_catalog.repository.User;
import com.dfedorino.user_catalog.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    List<User> all() {
        log.debug("request to '/users'");
        return service.getAllUsers();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return service.createNewUser(newUser);
    }

    @GetMapping("/users/{login}")
    User readUser(@PathVariable String login) {
        return service.getUserByLogin(login);
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return service.updateUserById(id, newUser);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        service.deleteById(id);
    }
}
