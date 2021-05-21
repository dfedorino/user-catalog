package com.dfedorino.user_catalog.presentation.controller;

import com.dfedorino.user_catalog.application.UserRepository;
import com.dfedorino.user_catalog.presentation.model.User;
import com.dfedorino.user_catalog.presentation.model.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private final UserRepository repository;

    UserController (UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> all() {
        List<User> all = new ArrayList<>();
        repository.findAll().forEach(all::add);
        return all;
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User readUser(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setFamilyName(newUser.getFamilyName());
                    user.setDateOfBirth(newUser.getDateOfBirth());
                    user.setAddress(newUser.getAddress());
                    user.setPhoneNumber(newUser.getPhoneNumber());
                    return repository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
