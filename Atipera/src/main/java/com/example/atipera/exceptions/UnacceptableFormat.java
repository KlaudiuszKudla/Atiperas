package com.example.atipera.exceptions;

public class UnacceptableFormat extends RuntimeException {

    public UnacceptableFormat(String message) {
        super(message);
    }

    public UnacceptableFormat(String message, Throwable cause) {
        super(message, cause);
    }

    public UnacceptableFormat(Throwable cause) {
        super(cause);
    }
}
