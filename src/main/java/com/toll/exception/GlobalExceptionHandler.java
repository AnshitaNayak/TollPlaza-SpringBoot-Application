package com.toll.exception;

import com.toll.dto.ErrorResponseDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SamePincodeException.class)
    public ResponseEntity<ErrorResponseDTO> handleSame(SamePincodeException ex){
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex){
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO("Invalid source or destination pincode"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(){
        return ResponseEntity.internalServerError()
                .body(new ErrorResponseDTO("Something went wrong"));
    }
}