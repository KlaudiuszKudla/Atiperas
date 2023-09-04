package com.example.atipera.exceptions;

public class UserNotExistWithName extends RuntimeException{

    public UserNotExistWithName(String message) {
        super(message);
    }

    public UserNotExistWithName(Throwable cause) {
        super(cause);
    }

    public UserNotExistWithName(String message, Throwable cause) {
        super(message, cause);
    }
}
