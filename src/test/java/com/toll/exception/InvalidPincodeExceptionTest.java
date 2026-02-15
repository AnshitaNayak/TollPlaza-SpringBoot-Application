package com.toll.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidPincodeExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        InvalidPincodeException ex =
                new InvalidPincodeException("Invalid pincode");

        assertEquals("Invalid pincode", ex.getMessage());
    }
}