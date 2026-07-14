package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class CustomerNotFoundException extends BadRequestException {
    public CustomerNotFoundException(String customerId) {
        super("customer not found with id: " + customerId);;
    }
}
