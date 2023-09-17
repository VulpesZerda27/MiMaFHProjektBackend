package com.mima.mimafhprojektbackend.controller;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public @ResponseBody List<MyUser> GetAllUsers() {
        return userService.GetAllUsers();
    }

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

}
