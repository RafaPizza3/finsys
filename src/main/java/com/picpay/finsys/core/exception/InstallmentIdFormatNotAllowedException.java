package com.picpay.finsys.core.exception;

public class InstallmentIdFormatNotAllowedException extends RuntimeException {
    public InstallmentIdFormatNotAllowedException(String installmentId) {
        super("the following installment id is not allowed in this format: " + installmentId);
    }
}
