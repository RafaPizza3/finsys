package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.InstallmentPaymentPriorityException;
import com.picpay.finsys.core.gateway.ContractGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstallmentPaymentPriorityValidation {
    private final ContractGateway contractGateway;

    public void validate(String contractId, String installmentId) throws InstallmentPaymentPriorityException {
        ContractDomain contract = contractGateway.findById(contractId);

        InstallmentDomain priorInstallment = contract.getInstallments().get(Integer.parseInt(installmentId) - 2);

        if (priorInstallment.getStatus() != InstallmentStatus.PAID) {
            throw new InstallmentPaymentPriorityException(contractId, installmentId);
        }
    }
}
