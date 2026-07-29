package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.ExceededInstallmentAmountInPaymentException;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Component;

@Component
public class InstallmentPaymentAmountValidation {
    public void validate(Double installmentAmount, Double paidAmount, Double paymentAmount) throws ExceededInstallmentAmountInPaymentException {
            if ((installmentAmount - paidAmount) < paymentAmount) {
                throw new ExceededInstallmentAmountInPaymentException(installmentAmount - paidAmount);
            }
    }

    public void validate(Double installmentAmount, Double paymentAmount) throws ExceededInstallmentAmountInPaymentException {
        if (installmentAmount < paymentAmount) {
            throw new ExceededInstallmentAmountInPaymentException(installmentAmount);
        }
    }
}
