package com.dfedorino.user_catalog.service;

import com.dfedorino.user_catalog.controller.UserController;
import com.dfedorino.user_catalog.repository.User;
import com.dfedorino.user_catalog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(UserController.class)
class UserServiceTest {
    @Autowired
    UserService userService;
    @MockBean
    UserRepository mockUserRepository;

    @Test
    void testUserService_whenServiceIsCalled_thenItIsNotNull() {
        assertThat(userService).isNotNull();
    }

    @Test
    void testCreateNewUser_whenNewUserIsCreated_thenSaltedPasswordIsGenerated() {
        String login = "login";
        String givenPassword = "password";
        User user = new User();
        user.setLogin(login);
        user.setPassword(givenPassword);
        User createdUser = userService.createUserWithHashedPassword(user);
        assertThat(createdUser.getPassword()).isNotEqualTo(user.getPassword());
    }
}