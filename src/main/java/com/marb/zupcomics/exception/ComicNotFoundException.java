package com.marb.zupcomics.exception;

public class ComicNotFoundException extends RuntimeException {
    public ComicNotFoundException(String message) {
        super(message);
    }
}
