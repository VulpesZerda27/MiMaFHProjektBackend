package com.mima.mimafhprojektbackend.controller;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.security.UserPrincipal;
import com.mima.mimafhprojektbackend.security.UserPrincipalAuthenticationToken;
import com.mima.mimafhprojektbackend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userid}")
    public MyUser getUserById(@PathVariable Long userid) {
        return userService.getUserById(userid);
    }

    @PostMapping
    public ResponseEntity<MyUser> createUser(@Valid @RequestBody MyUser myUser) {
        MyUser createdUser = userService.createUser(myUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!userPrincipal.getUserId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    }
