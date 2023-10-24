package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.exceptions.EmailAlreadyRegisteredException;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.Role;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetUserById() {
        // Mock data
        Long userId = 1L;
        MyUser user = new MyUser();
        user.setId(userId);

        // Mock behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Action
        MyUser result = userService.getUserById(userId);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testGetUserByEmail() {
        // Mock data
        String email = "test@test.com";
        MyUser user = new MyUser();
        user.setEmail(email);

        // Mock behavior
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Action
        MyUser result = userService.getUserByEmail(email);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        // Mock data
        MyUser user = new MyUser();
        user.setEmail("test@test.com");

        // Mock behavior
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Action & Assert
        assertThrows(EmailAlreadyRegisteredException.class, () -> userService.createUser(user));
    }

    @Test
    void testCreateUser() throws EmailAlreadyRegisteredException {
        // Mock data
        MyUser user = new MyUser();
        user.setEmail("newuser@test.com");
        user.setPassword("plaintextpassword");

        Role role = new Role();

        role.setName("USER");
        role.setId(1L);

        // Mock behavior
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(MyUser.class))).thenReturn(user);
        when(roleService.findByName("USER")).thenReturn(role);

        // Action
        MyUser result = userService.createUser(user);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testDeleteUserById_NotFound() {
        // Mock data
        Long userId = 1L;

        // Mock behavior
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(NoSuchElementException.class, () -> userService.deleteUserById(userId));
    }
}
