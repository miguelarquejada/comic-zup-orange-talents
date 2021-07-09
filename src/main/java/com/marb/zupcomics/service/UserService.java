package com.marb.zupcomics.service;

import com.marb.zupcomics.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<User> saveUser(User user);
    ResponseEntity<User> saveUserComics(Long userId, Long comicId);
    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<User> getUser(Long id);
}
