package com.javarush.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(int id) {
        super("Entity not found with id - " + id);
    }

}
