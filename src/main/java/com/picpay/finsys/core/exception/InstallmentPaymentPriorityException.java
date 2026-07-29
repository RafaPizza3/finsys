package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class InstallmentPaymentPriorityException extends BadRequestException {
    public InstallmentPaymentPriorityException(String contractId, String installmentId) {
        super("the contract with id " + contractId + " has one or more unpaid installments prior to installment with id " + installmentId);
    }
}
