package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class NewLowerContractPeriodException extends BadRequestException {
    public NewLowerContractPeriodException() {
        super("new contract value must be bigger than or equals original value");
    }
}
