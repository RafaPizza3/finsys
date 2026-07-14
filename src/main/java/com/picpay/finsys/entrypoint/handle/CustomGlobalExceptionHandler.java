package com.picpay.finsys.entrypoint.handle;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomGlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex) {
        var error = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());
        var error = new ApiError(errors, HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}