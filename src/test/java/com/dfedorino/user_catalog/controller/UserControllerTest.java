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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

//    @Test
    void testGetAll_FilledDatabase_ShouldReturnListWithAllUsers() throws Exception {
        User user1 = new UserBuilder().login("login1").password("pass1").email("email1").build();
        given(service.getAllUsers()).willReturn(List.of(user1));
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(user1))));
    }

    @Test
    void testPostAll_FilledDatabase_ShouldReturnListWithAllUsersAndNewUser() throws Exception {
        User user2 = new UserBuilder().login("login2").password("pass2").email("email2").build();
        given(service.createNewUser(user2)).willReturn(user2);
        String user2json = objectMapper.writeValueAsString(user2);
        RequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user2json);
        this.mockMvc.perform(postRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(user2json));
    }

//    @Test
    void testGetOne_GetNonExistingUserFromFilledDatabase_ShouldThrowException() throws Exception {
        this.mockMvc.perform(get("/users/999"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Could not find user 999"));
    }

//    @Test
    void testPutOne_UpdateNonExistingUserFromFilledDatabase_ShouldThrowException() throws Exception {
        User user3 = new UserBuilder().login("3").password("3").email("3").build();
        String user3Json = objectMapper.writeValueAsString(user3);
        RequestBuilder putRequest = put("/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user3Json);
        this.mockMvc.perform(putRequest)
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Could not find user 999"));
    }
}