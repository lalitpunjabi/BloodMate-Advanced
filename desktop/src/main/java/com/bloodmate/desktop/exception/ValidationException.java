package com.bloodmate.desktop.exception;

/**
 * Custom exception for validation errors in the BloodMate application
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}