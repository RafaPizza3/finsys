package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.exception.InstallmentNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstallmentExistenceValidation {
    private final ContractGateway contractGateway;

    public void validate(String contractId, String installmentId) throws InstallmentNotFoundException {
        try {
            ContractDomain contract = contractGateway.findById(contractId);

            InstallmentDomain installment = contract.getInstallments().get(Integer.parseInt(installmentId) - 1);

            if (installment == null) {
                throw new InstallmentNotFoundException(contractId, installmentId);
            }
        } catch (IndexOutOfBoundsException iobe) {
            throw new InstallmentNotFoundException(contractId, installmentId);
        }
    }
}
