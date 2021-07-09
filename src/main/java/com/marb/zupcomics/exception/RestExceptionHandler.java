package com.marb.zupcomics.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                 HttpStatus status, WebRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setMessage("Invalid fields");
        err.setError(status.name());
        err.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<StandardError> handleUserAlreadyExists(UserAlreadyExistsException ex,
                                                                    HttpServletRequest request) {
        StandardError err = generateStandardError(ex, request, HttpStatus.CONFLICT);
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<StandardError> handleUserNotFound(UserNotFoundException ex,
                                                               HttpServletRequest request) {
        StandardError err = generateStandardError(ex, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserComicAlreadyExistsException.class)
    protected ResponseEntity<StandardError>
    handleUserComicAlreadyExists(UserComicAlreadyExistsException ex, HttpServletRequest request) {
        StandardError err = generateStandardError(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ComicNotFoundException.class)
    protected ResponseEntity<StandardError>
    handleComicNotFound(ComicNotFoundException ex, HttpServletRequest request) {
        StandardError err = generateStandardError(ex, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    private StandardError
    generateStandardError(RuntimeException ex, HttpServletRequest request, HttpStatus status) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(status.name());
        err.setMessage(ex.getMessage());
        err.setPath(request.getRequestURI());
        return err;
    }

}
