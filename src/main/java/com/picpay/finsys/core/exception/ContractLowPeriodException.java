package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class ContractLowPeriodException extends BadRequestException {
    public ContractLowPeriodException() {
        super("contract period must be at least 6");
    }
}
