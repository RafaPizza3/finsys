package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class ActiveCustomerException extends BadRequestException {
    public ActiveCustomerException() {
        super("customer must not be active");
    }
}
