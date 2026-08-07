package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class CustomerNotFoundException extends BadRequestException {
    public CustomerNotFoundException() {
        super("customer not found");;
    }
}
