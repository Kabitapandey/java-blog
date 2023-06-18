package com.example.blogapp.exception;

public class EmailExistsException extends RuntimeException {
    private String msg;

    public EmailExistsException(String msg) {
        super(String.format(msg));
        this.msg = msg;
    }
}
