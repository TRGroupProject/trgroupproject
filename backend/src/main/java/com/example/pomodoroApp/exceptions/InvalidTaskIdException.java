package com.example.pomodoroApp.exceptions;

public class InvalidTaskIdException extends RuntimeException {
    private String message;

    public InvalidTaskIdException (String message) {
        super(message);
        this.message = message;
    }
}
