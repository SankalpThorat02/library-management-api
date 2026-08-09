package com.sankalp.library.exception;

public class BookNotBorrowedException extends RuntimeException {

    public BookNotBorrowedException(String message) {
        super(message);
    }
}
