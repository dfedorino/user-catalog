package com.dfedorino.user_catalog.presentation.controller;

import com.dfedorino.user_catalog.application.UserService;
import com.dfedorino.user_catalog.presentation.model.User;
import com.dfedorino.user_catalog.presentation.model.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService service;

    UserController (UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    List<User> all() {
        return service.getAllUsers();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return service.createNewUser(newUser);
    }

    @GetMapping("/users/{id}")
    User readUser(@PathVariable Long id) {
        return service.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return service.updateUserById(id, newUser).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        service.deleteById(id);
    }
}
