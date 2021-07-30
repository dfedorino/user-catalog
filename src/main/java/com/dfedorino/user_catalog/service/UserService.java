package com.dfedorino.user_catalog.service;

import com.dfedorino.user_catalog.repository.ClientDto;
import com.dfedorino.user_catalog.repository.ClientDtoImpl;
import com.dfedorino.user_catalog.repository.Contact;
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

    public List<ClientDto> getAllUsers() {
        List<ClientDto> all = new ArrayList<>();
        repository.findAll().forEach(user -> all.add(new ClientDtoImpl(user)));
        return all;
    }

    public ClientDto createNewUser(User user) {
        String username = user.getLogin();
        String email = user.getEmail();
        User userWithSameLogin = repository.findByLogin(username);
        User userWithSameEmail = repository.findByEmail(email);
        boolean userAlreadyExists = userWithSameLogin != null || userWithSameEmail != null;
        if (userAlreadyExists) {
            throw new UserAlreadyExistsException();
        } else {
            User saved = repository.save(createUserWithHashedPassword(user));
            return new ClientDtoImpl(saved);
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

    public ClientDto getUserByLogin(String login) {
        User found = repository.findByLogin(login);
        if (found == null) {
            throw new UserNotFoundException(login);
        }
        return new ClientDtoImpl(found);
    }

    public ClientDto updateUserByLogin(String login, ClientDto newUser) {
        User oldUser = repository.findByLogin(login);
        updateUserWith(oldUser, newUser);
        User saved = repository.save(oldUser);
        return new ClientDtoImpl(saved);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private void updateUserWith(User user, ClientDto newUser) {
        user.setLogin(newUser.getLogin());
        user.setEmail(newUser.getEmail());
        Contact newContact = new Contact();
        newContact.setPhoneNumber(newUser.getPhoneNumber());
        newContact.setStreet(newUser.getStreet());
        newContact.setZipCode(newUser.getZipCode());
        user.setContact(newContact);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(repository.findByLogin(username));
    }
}
