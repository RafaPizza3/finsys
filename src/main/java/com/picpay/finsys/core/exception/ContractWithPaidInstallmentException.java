package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class ContractWithPaidInstallmentException extends BadRequestException {
    public ContractWithPaidInstallmentException(String contractId) {
        super("the contract with id " + contractId + " already has one or more paid installments and cannot be canceled");
    }
}
