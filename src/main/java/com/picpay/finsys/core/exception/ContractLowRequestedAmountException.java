package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class ContractLowRequestedAmountException extends BadRequestException {
    public ContractLowRequestedAmountException() {
        super("contract requested amount must be at least 1000");
    }
}
