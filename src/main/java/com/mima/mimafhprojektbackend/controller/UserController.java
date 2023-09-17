package com.mima.mimafhprojektbackend.controller;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{userid}")
    public Optional<MyUser> getUserById(@PathVariable Long userid) {

        return userService.getUserById(userid);
    }

    @PostMapping
    public MyUser createUser(@Valid @RequestBody MyUser myUser) {
        return userService.createUser(myUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

    // A helper method to extract the email claim from the Authentication object
    private String getEmailFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // getUsername() returns the email for your UserPrincipal
    }
}
