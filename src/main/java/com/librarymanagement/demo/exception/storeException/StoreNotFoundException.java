package com.librarymanagement.demo.exception.storeException;

public class StoreNotFoundException extends RuntimeException{
    public StoreNotFoundException(String message) {
        super(message);
    }
}
