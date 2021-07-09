package com.marb.zupcomics.controller;

import com.marb.zupcomics.model.User;
import com.marb.zupcomics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/users/{userId}/comics/{comicId}")
    public ResponseEntity<User> saveUserComics(@PathVariable Long userId, @PathVariable Long comicId) {
        return userService.saveUserComics(userId, comicId);
    }
}
