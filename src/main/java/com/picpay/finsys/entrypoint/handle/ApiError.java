package com.picpay.finsys.entrypoint.handle;

import lombok.Getter;

import java.util.List;

@Getter
public class ApiError {
    private List<String> errors;
    private String message;
    private String errorType;

    public ApiError(List<String> errors, String errorType) {
        this.errors = errors;
        this.errorType = errorType;
    }

    public ApiError(String message, String errorType) {
        this.message = message;
        this.errorType = errorType;
    }
}
