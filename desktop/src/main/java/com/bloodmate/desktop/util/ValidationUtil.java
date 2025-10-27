package com.bloodmate.desktop.util;

import com.bloodmate.desktop.exception.ValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Utility class for validation operations in the BloodMate application
 */
public class ValidationUtil {
    
    /**
     * Validates that a string is not null or empty
     * @param value The string to validate
     * @param fieldName The name of the field being validated
     * @throws ValidationException if the string is null or empty
     */
    public static void validateNotEmpty(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required");
        }
    }
    
    /**
     * Validates that a date range is valid (start date is before end date)
     * @param startDate The start date
     * @param endDate The end date
     * @param fieldName The name of the field being validated
     * @throws ValidationException if the date range is invalid
     */
    public static void validateDateRange(LocalDate startDate, LocalDate endDate, String fieldName) throws ValidationException {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new ValidationException(fieldName + " end date must be after start date");
        }
    }
    
    /**
     * Validates that a date range is valid (start date is before end date)
     * @param startDate The start date
     * @param endDate The end date
     * @param fieldName The name of the field being validated
     * @throws ValidationException if the date range is invalid
     */
    public static void validateDateRange(LocalDateTime startDate, LocalDateTime endDate, String fieldName) throws ValidationException {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new ValidationException(fieldName + " end date must be after start date");
        }
    }
    
    /**
     * Validates that a number is positive
     * @param value The number to validate
     * @param fieldName The name of the field being validated
     * @throws ValidationException if the number is not positive
     */
    public static void validatePositive(int value, String fieldName) throws ValidationException {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be greater than 0");
        }
    }
    
    /**
     * Validates that a number is positive
     * @param value The number to validate
     * @param fieldName The name of the field being validated
     * @throws ValidationException if the number is not positive
     */
    public static void validatePositive(double value, String fieldName) throws ValidationException {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be greater than 0");
        }
    }
    
    /**
     * Validates that a string represents a valid integer
     * @param value The string to validate
     * @param fieldName The name of the field being validated
     * @return The parsed integer value
     * @throws ValidationException if the string is not a valid integer
     */
    public static int validateInteger(String value, String fieldName) throws ValidationException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " must be a valid integer", e);
        }
    }
    
    /**
     * Validates that a string represents a valid double
     * @param value The string to validate
     * @param fieldName The name of the field being validated
     * @return The parsed double value
     * @throws ValidationException if the string is not a valid double
     */
    public static double validateDouble(String value, String fieldName) throws ValidationException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " must be a valid number", e);
        }
    }
}