package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class FinishedContractException extends BadRequestException {
    public FinishedContractException(String contractId) {
        super("the contract with id " + contractId + " is already finished");
    }
}
