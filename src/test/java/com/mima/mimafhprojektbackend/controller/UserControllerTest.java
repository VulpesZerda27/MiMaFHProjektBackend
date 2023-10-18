package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService myUserService;


    /*@org.junit.jupiter.api.Test
    void testGetUserById() {
        // Arrange
        Long userId = 1L;
        MyUser user = new MyUser();
        user.setId(userId);
        user.setEmail("test@test.com");

        // Mock the behavior of myUserService
        when(myUserService.getUserById(userId)).thenReturn(Optional.of(user));

        // Action
        ResponseEntity<MyUser> userResponse = userController.getUserById(userId);

        //Assert
        assertNotNull(userResponse);
        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        assertEquals(userId, userResponse.getBody().getId());
        assertEquals("test@test.com", userResponse.getBody().getEmail());
    }

    @org.junit.jupiter.api.Test
    void testGetUserById_NotFound() {
        // Mock data
        Long userId = 1L;
        MyUser user = new MyUser();
        user.setId(userId);

        // Mock behavior
        when(myUserService.getUserById(100L)).thenReturn(null);

        // Action
        ResponseEntity<MyUser> userResponse = userController.getUserById(100L);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, userResponse.getStatusCode());
        assertEquals(false, userResponse.hasBody());
    }

    @Test
    void testUpdateUser() {
        // Mock data
        Long userId = 1L;
        MyUser existingUser = new MyUser();
        existingUser.setId(userId);
        existingUser.setFirstName("Markus");

        MyUserDTO userDTO = new MyUserDTO();
        userDTO.setLastName("Huber");

        MyUser updatedUser = new MyUser();
        updatedUser.setId(userId);
        updatedUser.setFirstName("Markus");
        updatedUser.setLastName("Huber");

        // Mock behavior
        when(adminService.updateUser(userId, userDTO)).thenReturn(updatedUser);

        // Action
        ResponseEntity<MyUser> userResponse = adminController.updateUser(userId, userDTO);

        // Assert
        assertTrue(userResponse.hasBody());
        assertEquals(HttpStatus.OK ,userResponse.getStatusCode());
        assertEquals("Markus", userResponse.getBody().getFirstName());
        assertEquals("Huber", userResponse.getBody().getLastName());
    }

    @Test
    void testDeleteUser() {
        // Mock data
        Long userId = 1L;
        MyUser toDeleteUser = new MyUser();
        toDeleteUser.setId(userId);
        Optional<MyUser> toDeleteOptional = Optional.of(toDeleteUser);

        // Mock behaviour
        when(myUserService.deleteUserById(userId)).thenReturn(toDeleteOptional);

        // Action
        ResponseEntity<MyUser> userResponse = userController.deleteUserById(userId);

        // Assert
        assertTrue(userResponse.hasBody());
        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
    }

    @Test
    void testDeleteUser_NotFound() {
        // Mock data
        Long invalidUserId = 2L;

        // Mock behaviour
        when(myUserService.deleteUserById(invalidUserId)).thenReturn(null);

        // Action
        ResponseEntity<MyUser> userResponse = userController.deleteUserById(2L);

        // Assert
        assertFalse(userResponse.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, userResponse.getStatusCode());
    }*/
}

