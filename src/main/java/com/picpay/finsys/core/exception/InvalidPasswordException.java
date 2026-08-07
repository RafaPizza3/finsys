package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class InvalidPasswordException extends BadRequestException {
    public InvalidPasswordException() {
        super("the password is invalid");
    }
}
