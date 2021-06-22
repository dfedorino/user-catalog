package com.dfedorino.user_catalog.controller;

import com.dfedorino.user_catalog.repository.User;
import com.dfedorino.user_catalog.repository.UserRepository;
import com.dfedorino.user_catalog.security.CustomPasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    CustomPasswordEncoder passwordEncoder;

    @Test
    public void testMockMvc_whenAnyTestIsRun_thenMockMvcIsInitialized() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void testLogin_whenPostExistingUserDataRequestToLogin_thenOkResponse() throws Exception {
        String login = "login";
        String password = "password";
        String storedPassword = new String(passwordEncoder.generateEncryptedSaltedBytes(login, password));
        // user as if it is was stored in a real db
        User stored = new User();
        stored.setLogin(login);
        stored.setPassword(storedPassword);
        given(userRepository.findByLogin(login)).willReturn(stored);
        // emulate login
        AuthController.LoginPassword loginPassword = new AuthController.LoginPassword();
        loginPassword.setLogin(login);
        loginPassword.setPassword(password);
        RequestBuilder postWithExistingData = post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginPassword));
        // test response
        mockMvc.perform(postWithExistingData)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testLogin_whenPostNonExistingUserDataRequestToLogin_then401Response() throws Exception {
        String login = "login";
        String password = "password";
        String storedPassword = new String(passwordEncoder.generateEncryptedSaltedBytes(login, password));
        // user as if it is was stored in a real db
        User stored = new User();
        stored.setLogin(login);
        stored.setPassword(storedPassword);
        given(userRepository.findByLogin(login)).willReturn(null);
        // emulate login
        AuthController.LoginPassword loginPassword = new AuthController.LoginPassword();
        loginPassword.setLogin(login);
        loginPassword.setPassword(password);
        RequestBuilder postWithExistingData = post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginPassword));
        // test response
        mockMvc.perform(postWithExistingData)
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}