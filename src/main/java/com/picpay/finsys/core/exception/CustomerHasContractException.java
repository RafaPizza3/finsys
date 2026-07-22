package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class CustomerHasContractException extends BadRequestException {
    public CustomerHasContractException() {
        super("customer must not have an active contract");
    }
}
