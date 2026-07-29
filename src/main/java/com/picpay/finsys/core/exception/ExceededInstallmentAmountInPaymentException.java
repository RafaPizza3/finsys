package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class ExceededInstallmentAmountInPaymentException extends BadRequestException {
    public ExceededInstallmentAmountInPaymentException(Double amountDuePayment) {
        super("the payment amount must be lower or equals the installment due payment amount: " + amountDuePayment);
    }
}
