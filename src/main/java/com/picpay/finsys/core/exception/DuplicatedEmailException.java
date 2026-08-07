package com.picpay.finsys.core.exception;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
        super("the requested email already exists");
    }
}
