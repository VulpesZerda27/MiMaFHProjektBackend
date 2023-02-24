package com.mima.mimafhprojektbackend.controller;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public @ResponseBody List<MyUser> GetAllUsers() {
        return userService.GetAllUsers();
    }

    @GetMapping("/{userid}")
    public Optional<MyUser> getUserById(@PathVariable Long userid) {
        return userService.getUserById(userid);
    }

    @PostMapping
    public MyUser createUser(@RequestBody @Valid MyUser myUser) {
        return userService.createUser(myUser);
    }

    @PutMapping("/{userId}")
    public MyUser updateUser(@PathVariable Long userId, @RequestBody @Valid MyUser userDetails) {
        return userService.updateUser(userId, userDetails);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

}
