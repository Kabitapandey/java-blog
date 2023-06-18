package com.example.blogapp.exception;

public class NotFoundException extends RuntimeException {
    private String resource;

    public NotFoundException(String resource) {
        super(String.format("%s not found", resource));
        this.resource = resource;
    }
}
