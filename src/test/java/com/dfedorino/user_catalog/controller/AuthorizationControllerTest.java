package com.dfedorino.user_catalog.controller;

import com.dfedorino.user_catalog.service.UserService;
import com.dfedorino.user_catalog.controller.AuthorizationController;
import com.dfedorino.user_catalog.repository.UserBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorizationController.class)
@AutoConfigureMockMvc
class AuthorizationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;

    @Test
    void loginPage_whenGetReceived_thenSendAuthPage() throws Exception {
        this.mockMvc.perform(get("/auth"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Authentication page, please send login and password!"));
    }

    @Test
    void authenticate_whenExistingLoginIsSent_thenPutCookieAndRedirectToHomePage() throws Exception {
        given(service.getUserByLogin("login1"))
                .willReturn(new UserBuilder()
                        .login("login1")
                        .password("pass1")
                        .email("email1").build());
        RequestBuilder postRequest = post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                            "\"login\":" + "\"login1\"," +
                            "\"password\":" + "\"pass1\"" +
                        "}");
        this.mockMvc.perform(postRequest)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/users"))
                .andExpect(cookie().value("Authorization", "JwtToken"));
    }

    @Test
    void authenticate_whenNonExistingLoginIsSent_thenPutCookieAndRedirectToHomePage() throws Exception {
        RequestBuilder postRequest = post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                            "\"login\":" + "\"login999\"," +
                            "\"password\":" + "\"pass999\"" +
                        "}");
        this.mockMvc.perform(postRequest)
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Could not find user login999"));
    }
}