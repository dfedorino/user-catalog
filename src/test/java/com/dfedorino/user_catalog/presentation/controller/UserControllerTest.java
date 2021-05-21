package com.dfedorino.user_catalog.presentation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAll_FilledDatabase_ShouldReturnListWithAllUsers() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testPostAll_FilledDatabase_ShouldReturnListWithAllUsersAndNewUser() throws Exception {
        String user4Json = "{\"firstName\":\"first name 4\",\"familyName\":\"family name 4\",\"dateOfBirth\":\"1970-01-04\",\"address\":\"address 4\",\"phoneNumber\":\"number 4\"}";
        RequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON )
                .content(user4Json);
        this.mockMvc.perform(postRequest)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetOne_GetExistingUserFromFilledDatabase_ShouldReturnExistingUser() throws Exception {
        this.mockMvc.perform(get("/users/3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetOne_GetNonExistingUserFromFilledDatabase_ShouldThrowException() throws Exception {
        this.mockMvc.perform(get("/users/999"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Could not find user 999"));
    }
}