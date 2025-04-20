package com.librarymanagement.demo.exception.bookPublisherException;

public class BookPublisherNotFoundException extends RuntimeException {
    public BookPublisherNotFoundException(String message) {
        super(message);
    }
}
