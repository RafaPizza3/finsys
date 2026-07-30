package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class InstallmentNotFoundException extends BadRequestException {
    public InstallmentNotFoundException(String contractId, String installmentId) {
        super("there is no installment with id " + installmentId + " in the contract with id " + contractId);
    }
}
