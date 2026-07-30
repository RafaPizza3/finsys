package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class InactiveCustomerException extends BadRequestException {
    public InactiveCustomerException() {
        super("customer must not be inactive");
    }
}
