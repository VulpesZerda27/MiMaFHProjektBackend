package com.mima.mimafhprojektbackend.controller;
import com.mima.mimafhprojektbackend.dto.MyUserDTO;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/{userId}")
    public ResponseEntity<MyUser> getUserById(@PathVariable Long userId) {
        MyUser user = userService.getUserById(userId);
        if (authService.isLoggedInUserOrAdmin(user)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<MyUser> createUser(@Valid @RequestBody MyUser myUser) {
        MyUser createdUser = userService.createUser(myUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        MyUser user = userService.getUserById(userId);
        if (authService.isLoggedInUserOrAdmin(user)) {
            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<MyUser> updateUser(@PathVariable Long userId, @RequestBody @Valid MyUserDTO userDTO) {
        MyUser user = userService.getUserById(userId);
        if (authService.isLoggedInUserOrAdmin(user)) {
            return new ResponseEntity<>(userService.updateUser(userId, userDTO), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public List<MyUser> getAllUsers() {
        return userService.getAllUsers();
    }
    }
