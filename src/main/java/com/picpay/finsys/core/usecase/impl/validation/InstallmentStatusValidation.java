package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.CanceledInstallmentException;
import com.picpay.finsys.core.exception.PaidInstallmentException;
import org.springframework.stereotype.Component;

@Component
public class InstallmentStatusValidation {
    public void validate(InstallmentDomain installment, String contractId) throws PaidInstallmentException, CanceledInstallmentException {
        if(installment.getStatus() == InstallmentStatus.PAID) {
            throw new PaidInstallmentException(contractId, installment.getId());
        }

        if(installment.getStatus() == InstallmentStatus.CANCELED) {
            throw new CanceledInstallmentException(contractId, installment.getId());
        }
    }
}
