package com.dfedorino.user_catalog.presentation.controller;

import com.dfedorino.user_catalog.application.UserService;
import com.dfedorino.user_catalog.presentation.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDate;
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

    @BeforeEach
    void setUpObjectMapper() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void testGetAll_FilledDatabase_ShouldReturnListWithAllUsers() throws Exception {
        User user1 = new User("1", "1", LocalDate.of(1991, 1, 1), "1", "1");
        given(service.getAllUsers()).willReturn(List.of(user1));
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(user1))));
    }

    @Test
    void testPostAll_FilledDatabase_ShouldReturnListWithAllUsersAndNewUser() throws Exception {
        User user2 = new User("2", "2", LocalDate.of(1992, 2, 2), "2", "2");
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

    @Test
    void testGetOne_GetNonExistingUserFromFilledDatabase_ShouldThrowException() throws Exception {
        this.mockMvc.perform(get("/users/999"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Could not find user 999"));
    }

    @Test
    void testPutOne_UpdateNonExistingUserFromFilledDatabase_ShouldThrowException() throws Exception {
        User user3 = new User("3", "3", LocalDate.of(1993, 3, 3), "3", "3");
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