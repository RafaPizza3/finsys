package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class CustomerTooYoungException extends BadRequestException {
    public CustomerTooYoungException() {
        super("the customer must be 18 years old or older");
    }
}
