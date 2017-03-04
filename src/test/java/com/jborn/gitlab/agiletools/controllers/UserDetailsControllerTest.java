package com.jborn.gitlab.agiletools.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jborn.gitlab.agiletools.dto.UserDetails;
import com.jborn.gitlab.agiletools.response.UserDetailsResponse;
import com.jborn.gitlab.agiletools.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.jborn.gitlab.agiletools.Constants.UserApi.USER_DETAILS;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser
@WebMvcTest(UserDetailsController.class)
@ActiveProfiles("test")
public class UserDetailsControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean UserService userService;

    @Test
    public void getUserInfo() throws Exception {
        given(userService.currentUser()).willReturn(new UserDetails()
                .setId(1L)
                .setName("name")
                .setUserName("username")
                .setEmail("maksaimer@gmail.com"));

        UserDetailsResponse response = new UserDetailsResponse()
                .setId(1L)
                .setName("name")
                .setUsername("username");

        mockMvc.perform(get(USER_DETAILS).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }
}