package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getUserById_validId_shouldReturnUser() throws Exception {
        MyUser user = new MyUser();
        user.setUserId(1L);
        user.setUserFirstName("John");
        user.setUserLastName("Doe");
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect((ResultMatcher) jsonPath("$.id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void getUserById_invalidId_shouldReturnNotFound() throws Exception {
        when(userService.getUserById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createUser_validInput_shouldReturnCreatedUser() throws Exception {
        MyUser user = new MyUser();
        user.setUserFirstName("John");
        user.setUserLastName("Doe");
        MyUser createdUser = new MyUser();
        createdUser.setUserId(1L);
        createdUser.setUserFirstName("John");
        createdUser.setUserLastName("Doe");
        when(userService.createUser(any(MyUser.class))).thenReturn(createdUser);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"name\":\"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$.name", is("John Doe")));
    }
    // You might also add more tests here like checking for bad input or unauthorized access etc.
}

