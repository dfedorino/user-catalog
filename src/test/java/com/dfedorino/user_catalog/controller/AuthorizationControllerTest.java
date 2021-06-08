package com.dfedorino.user_catalog.controller;

import com.dfedorino.user_catalog.repository.User;
import com.dfedorino.user_catalog.repository.UserBuilder;
import com.dfedorino.user_catalog.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorizationController.class)
@AutoConfigureMockMvc
class AuthorizationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;

    @Test
    void authenticate_whenExistingLoginIsSent_thenSendTokenToTheResponseBody() throws Exception {
        String login = "login1";
        User user = new UserBuilder()
                .login(login)
                .password("pass1")
                .email("email1").build();
        given(service.getUserByLogin(login))
                .willReturn(user);
        String json = new ObjectMapper().writeValueAsString(user);
        RequestBuilder postRequest = post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mockMvc.perform(postRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{" +
                        "\"access_token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZmVkb3Jpbm8uY29tIn0.5I9gTeH1ebjD2MXbAIOR7i9-_bYPzuhmS-GEkbmcbWA\"," +
                        "\"token_type\":\"Bearer\"" +
                                "}"));
    }

    @Test
    void authenticate_whenNonExistingLoginIsSent_thenPutCookieAndRedirectToHomePage() throws Exception {
        User user = new UserBuilder()
                .login("login999")
                .password("pass999")
                .email("email999").build();
        String json = new ObjectMapper().writeValueAsString(user);
        RequestBuilder postRequest = post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mockMvc.perform(postRequest)
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Could not find user login999"));
    }
}