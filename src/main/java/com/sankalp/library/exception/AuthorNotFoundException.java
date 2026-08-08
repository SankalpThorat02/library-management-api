package com.sankalp.library.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
