package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class InvalidEmailException extends BadRequestException {
    public InvalidEmailException() {
        super("the requested email is not valid");
    }
}
