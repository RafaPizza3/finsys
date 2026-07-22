package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class InvalidZipCodeException extends BadRequestException {
    public InvalidZipCodeException(String zipCode) {
        super("the following zip code is invalid: " + zipCode);
    }
}
