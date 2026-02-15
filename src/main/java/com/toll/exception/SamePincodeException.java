package com.toll.exception;

public class SamePincodeException extends RuntimeException {
    public SamePincodeException(String message) {
        super(message);
    }
}