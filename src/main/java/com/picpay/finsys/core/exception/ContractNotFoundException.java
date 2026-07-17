package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class ContractNotFoundException extends BadRequestException {
    public ContractNotFoundException(String contractId) {
        super("contract not found with id: " + contractId);
    }
}
