package com.mima.mimafhprojektbackend.controller;
import com.mima.mimafhprojektbackend.dto.MyUserDTO;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import com.mima.mimafhprojektbackend.security.UserPrincipal;
import com.mima.mimafhprojektbackend.security.UserPrincipalAuthenticationToken;
import com.mima.mimafhprojektbackend.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
