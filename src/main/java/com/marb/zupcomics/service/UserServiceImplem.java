package com.marb.zupcomics.service;

import com.marb.zupcomics.client.marvel.MarvelComicClient;
import com.marb.zupcomics.exception.UserAlreadyExistsException;
import com.marb.zupcomics.exception.UserComicAlreadyExistsException;
import com.marb.zupcomics.exception.UserNotFoundException;
import com.marb.zupcomics.model.User;
import com.marb.zupcomics.model.comic.Comic;
import com.marb.zupcomics.model.creator.Creator;
import com.marb.zupcomics.repository.ComicRepository;
import com.marb.zupcomics.repository.CreatorRepository;
import com.marb.zupcomics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplem implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private CreatorRepository creatorRepository;

    @Autowired
    private MarvelComicClient marvelComicClient;

    @Override
    public ResponseEntity<User> saveUser(User user) {
        Optional<User> userOptional = userRepository.findByEmailOrCpf(user.getEmail(), user.getCpf());
        if(userOptional.isPresent())
            throw  new UserAlreadyExistsException("User already exists");

        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> saveUserComics(Long userId, Long comicId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Comic comic = marvelComicClient.getComicById(comicId);
        if(user.getComics().contains(comic))
            throw new UserComicAlreadyExistsException("This comic already exists for this user");

        for(Creator creator : comic.getCreators())
            creatorRepository.save(creator);

        comicRepository.save(comic);

        user.getComics().add(comic);
        userRepository.save(user);
        return getUser(userId);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList= userRepository.findAll();
        if(userList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
