package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.ContractWithPaidInstallmentException;
import org.springframework.stereotype.Component;

@Component
public class ContractHasPaidInstallmentValidation {
    public void validate(ContractDomain contract) throws ContractWithPaidInstallmentException {
        for (int i = 0; i == contract.getInstallments().size(); i++) {
            if (contract.getInstallments().get(i).getStatus() == InstallmentStatus.PAID) {
                throw new ContractWithPaidInstallmentException(contract.getId());
            }
        }
    }
}
