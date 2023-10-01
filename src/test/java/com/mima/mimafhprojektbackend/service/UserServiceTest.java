package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.exceptions.EmailAlreadyRegisteredException;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @org.junit.jupiter.api.Test
    void testGetUserById() {
        // Mock data
        Long userId = 1L;
        MyUser user = new MyUser();
        user.setId(userId);

        // Mock behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Action
        Optional<MyUser> result = userService.getUserById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @org.junit.jupiter.api.Test
    void testGetUserByEmail() {
        // Mock data
        String email = "test@test.com";
        MyUser user = new MyUser();
        user.setEmail(email);

        // Mock behavior
        when(userRepository.getMyUserByEmail(email)).thenReturn(Optional.of(user));

        // Action
        Optional<MyUser> result = userService.getUserByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @org.junit.jupiter.api.Test
    void testCreateUser_EmailAlreadyExists() {
        // Mock data
        MyUser user = new MyUser();
        user.setEmail("test@test.com");

        // Mock behavior
        when(userRepository.getMyUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Action & Assert
        assertThrows(EmailAlreadyRegisteredException.class, () -> userService.createUser(user));
    }

    @org.junit.jupiter.api.Test
    void testCreateUser() throws EmailAlreadyRegisteredException {
        // Mock data
        MyUser user = new MyUser();
        user.setEmail("newuser@test.com");
        user.setPassword("plaintextpassword");

        MyUser savedUser = new MyUser();
        savedUser.setEmail("newuser@test.com");
        // This will typically be some hashed value.
        savedUser.setPassword("hashedpassword");
        savedUser.setRoles(Arrays.asList("USER"));

        // Mock behavior
        when(userRepository.getMyUserByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.save(any(MyUser.class))).thenReturn(savedUser);

        // Action
        MyUser result = userService.createUser(user);

        // Assert
        assertEquals(savedUser, result);
    }

    @org.junit.jupiter.api.Test
    void testDeleteUserById() {
        // Mock data
        Long userId = 1L;
        MyUser user = new MyUser();
        user.setId(userId);

        // Mock behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Action
        Optional<MyUser> result = userService.deleteUserById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @org.junit.jupiter.api.Test
    void testDeleteUserById_NotFound() {
        // Mock data
        Long userId = 1L;

        // Mock behavior
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Action
        Optional<MyUser> result = userService.deleteUserById(userId);

        // Assert
        assertFalse(result.isPresent());
    }
}
