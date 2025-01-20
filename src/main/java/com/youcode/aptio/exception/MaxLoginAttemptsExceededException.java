package com.youcode.aptio.exception;

public class MaxLoginAttemptsExceededException extends RuntimeException {
    public MaxLoginAttemptsExceededException(String message) {
        super(message);
    }
}
