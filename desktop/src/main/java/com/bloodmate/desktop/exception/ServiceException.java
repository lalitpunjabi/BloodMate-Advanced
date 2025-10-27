package com.bloodmate.desktop.exception;

/**
 * Custom exception for service layer errors in the BloodMate application
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
    
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}