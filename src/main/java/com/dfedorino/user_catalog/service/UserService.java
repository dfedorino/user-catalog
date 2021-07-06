package com.dfedorino.user_catalog.service;

import com.dfedorino.user_catalog.repository.User;
import com.dfedorino.user_catalog.repository.UserDetailsImpl;
import com.dfedorino.user_catalog.repository.UserRepository;
import com.dfedorino.user_catalog.repository.exception.UserAlreadyExistsException;
import com.dfedorino.user_catalog.repository.exception.UserNotFoundException;
import com.dfedorino.user_catalog.security.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository repository;
    @Autowired
    CustomPasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        List<User> all = new ArrayList<>();
        repository.findAll().forEach(all::add);
        return all;
    }

    public User createNewUser(User user) {
        String username = user.getLogin();
        String email = user.getEmail();
        User userWithSameLogin = repository.findByLogin(username);
        User userWithSameEmail = repository.findByEmail(email);
        boolean userAlreadyExists = userWithSameLogin != null || userWithSameEmail != null;
        if (userAlreadyExists) {
            throw new UserAlreadyExistsException();
        } else {
            return repository.save(createUserWithHashedPassword(user));
        }
    }

    protected User createUserWithHashedPassword(User registrationData) {
        User userWithHashedPassword = new User();
        String login = registrationData.getLogin();
        String salt = passwordEncoder.generateSalt();
        String password = passwordEncoder.generateEncryptedSaltedBytes(salt, registrationData.getPassword());
        userWithHashedPassword.setLogin(login);
        userWithHashedPassword.setSalt(salt);
        userWithHashedPassword.setPassword(password);
        userWithHashedPassword.setEmail(registrationData.getEmail());
        userWithHashedPassword.setContact(registrationData.getContact());
        userWithHashedPassword.setAuthority(registrationData.getAuthority());
        return userWithHashedPassword;
    }

    public User getUserByLogin(String login) {
        User found = repository.findByLogin(login);
        if (found == null) {
            throw new UserNotFoundException(login);
        }
        return found;
    }

    public User updateUserById(Long id, User newUser) {
        User toBeUpdated = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return repository.save(getUpdatedUser(toBeUpdated, newUser));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private User getUpdatedUser(User user, User newUser) {
        user.setLogin(newUser.getLogin());
        user.setSalt(newUser.getSalt());
        user.setPassword(new String(passwordEncoder.generateEncryptedSaltedBytes(newUser.getSalt(), newUser.getPassword())));
        user.setEmail(newUser.getEmail());
        user.setContact(newUser.getContact());
        user.setAuthority(newUser.getAuthority());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(this.getUserByLogin(username));
    }
}
