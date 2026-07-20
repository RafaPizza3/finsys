package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class ActiveContractException extends BadRequestException {
    public ActiveContractException(String message) {
        super(message);
    }
}
