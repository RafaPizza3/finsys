package com.picpay.finsys.core.exception;

public class InvalidNumberException extends RuntimeException {
    public InvalidNumberException() {
        super("the requested number is invalid");
    }
}
