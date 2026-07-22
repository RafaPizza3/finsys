package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class ActiveContractException extends BadRequestException {
    public ActiveContractException() {
        super("contract must not be active");
    }
}
