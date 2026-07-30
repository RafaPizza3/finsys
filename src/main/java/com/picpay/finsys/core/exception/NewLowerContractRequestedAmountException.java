package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class NewLowerContractRequestedAmountException extends BadRequestException {
    public NewLowerContractRequestedAmountException() {
        super("the new requested amount must be bigger than the original amount");
    }
}
