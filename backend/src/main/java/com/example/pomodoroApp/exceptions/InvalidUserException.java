package com.example.pomodoroApp.exceptions;

public class InvalidUserException extends RuntimeException {
    private String message;

    public InvalidUserException(String message) {
        super(message);
        this.message = message;
    }
}