package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class CanceledContractException extends BadRequestException {
    public CanceledContractException(String contractId) {
        super("the contract with id " + contractId + " is already canceled");
    }
}
