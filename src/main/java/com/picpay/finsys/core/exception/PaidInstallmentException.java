package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class PaidInstallmentException extends BadRequestException {
    public PaidInstallmentException(String contractId, String installmentId) {
        super("the installment with id " + installmentId + " is already paid in contract with id " + contractId);
    }
}
