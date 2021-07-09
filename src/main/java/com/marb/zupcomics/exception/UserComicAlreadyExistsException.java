package com.marb.zupcomics.exception;

public class UserComicAlreadyExistsException extends RuntimeException {
    public UserComicAlreadyExistsException(String message) {
        super(message);
    }
}
