package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class CanceledInstallmentException extends BadRequestException {
    public CanceledInstallmentException(String contractId, String installmentId) {
        super("the installment with id " + installmentId + " is canceled in contract with id " + contractId);
    }
}
