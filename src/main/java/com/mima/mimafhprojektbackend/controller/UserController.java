package com.mima.mimafhprojektbackend.controller;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{userid}")
    public ResponseEntity<MyUser> getUserById(@PathVariable Long userid) {
        Optional<MyUser> user = userService.getUserById(userid);
        if(user != null){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MyUser> createUser(@Valid @RequestBody MyUser myUser) {
        MyUser createdUser = userService.createUser(myUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MyUser> deleteUserById(@PathVariable Long userId) {
        Optional<MyUser> deletedUser = userService.deleteUserById(userId);

        if(deletedUser != null) return new ResponseEntity<>(deletedUser.get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // A helper method to extract the email claim from the Authentication object
    private String getEmailFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // getUsername() returns the email for your UserPrincipal
    }
}
