package com.dfedorino.user_catalog.service;

import com.dfedorino.user_catalog.repository.UserRepository;
import com.dfedorino.user_catalog.repository.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        List<User> all = new ArrayList<>();
        repository.findAll().forEach(all::add);
        return all;
    }

    public User createNewUser(User user) {
        return repository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    public User getUserByLogin(String login) {
        return repository.findByLogin(login);
    }

    public Optional<User> updateUserById(Long id, User newUser) {
        return repository.findById(id)
                .map(user -> repository.save(getUpdatedUser(user, newUser)));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private User getUpdatedUser(User user, User newUser) {
        user.setLogin(newUser.getLogin());
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());
        return user;
    }

}
