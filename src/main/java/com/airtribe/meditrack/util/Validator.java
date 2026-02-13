package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;

public class Validator {
    
    public static void validateNotNull(Object obj, String fieldName) throws InvalidDataException {
        if (obj == null) {
            throw new InvalidDataException(fieldName + " cannot be null");
        }
    }

    public static void validateNotEmpty(String str, String fieldName) throws InvalidDataException {
        if (str == null || str.trim().isEmpty()) {
            throw new InvalidDataException(fieldName + " cannot be empty");
        }
    }

    public static void validatePositive(double value, String fieldName) throws InvalidDataException {
        if (value <= 0) {
            throw new InvalidDataException(fieldName + " must be positive");
        }
    }

    public static void validateAge(int age) throws InvalidDataException {
        if (age < 0 || age > 150) {
            throw new InvalidDataException("Invalid age: " + age);
        }
    }

    public static void validatePhoneNumber(String phone) throws InvalidDataException {
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new InvalidDataException("Invalid phone number. Must be 10 digits.");
        }
    }

    private Validator() {
    }
}
