package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.CanceledInstallmentException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.ExceededInstallmentAmountInPaymentException;
import com.picpay.finsys.core.exception.InstallmentNotFoundException;
import com.picpay.finsys.core.exception.InstallmentPaymentPriorityException;
import com.picpay.finsys.core.exception.PaidInstallmentException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.InstallmentPaymentUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractExistenceValidation;
import com.picpay.finsys.core.usecase.impl.validation.InstallmentExistenceValidation;
import com.picpay.finsys.core.usecase.impl.validation.InstallmentPaymentAmountValidation;
import com.picpay.finsys.core.usecase.impl.validation.InstallmentPaymentPriorityValidation;
import com.picpay.finsys.core.usecase.impl.validation.InstallmentStatusValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InstallmentPaymentUseCaseImpl implements InstallmentPaymentUseCase {
    private final ContractGateway contractGateway;

    private final ContractExistenceValidation contractExistenceValidation;
    private final InstallmentExistenceValidation installmentExistenceValidation;
    private final InstallmentPaymentPriorityValidation installmentPaymentPriorityValidation;
    private final InstallmentPaymentAmountValidation installmentPaymentAmountValidation;
    private final InstallmentStatusValidation installmentStatusValidation;

    @Override
    public InstallmentDomain execute(String contractId, String installmentId, Double paymentAmount) throws ContractNotFoundException, InstallmentNotFoundException, ExceededInstallmentAmountInPaymentException, InstallmentPaymentPriorityException, PaidInstallmentException, CanceledInstallmentException {
        contractExistenceValidation.validate(contractId);
        installmentExistenceValidation.validate(contractId, installmentId);

        if(!Objects.equals(installmentId, "1")) {
            installmentPaymentPriorityValidation.validate(contractId, installmentId);
        }

        ContractDomain contract = contractGateway.findById(contractId);
        InstallmentDomain installment = contract.getInstallments().get(Integer.parseInt(installmentId) - 1);

        installmentStatusValidation.validate(installment, contractId);

        if(installment.getPaidAmount() != null) {
            installmentPaymentAmountValidation.validate(installment.getAmount(), installment.getPaidAmount(), paymentAmount);
            installment.setPaidAmount(installment.getPaidAmount() + paymentAmount);
        }

        if(installment.getPaidAmount() == null) {
            installmentPaymentAmountValidation.validate(installment.getAmount(), paymentAmount);
            installment.setPaidAmount(paymentAmount);
        }

        installment.setStatus(InstallmentStatus.PAID);
        if (installment.getPaidAmount() < installment.getAmount()) {
            installment.setStatus(InstallmentStatus.PARTIALLY_PAID);
        }

        installment.setPaidDate(LocalDateTime.now());

        if(contract.getInstallments().getLast().getId() == installment.getId()
                && installment.getStatus() == InstallmentStatus.PAID) {
            contract.setStatus(ContractStatus.FINISHED);
        }

        ContractDomain newContract = buildUpdatedContract(contract, installment);

        contractGateway.update(newContract);

        return installment;
    }

    private ContractDomain buildUpdatedContract(
             ContractDomain contract,
             InstallmentDomain installment
    ) {
        int installmentIndex = Integer.parseInt(installment.getId()) - 1;

        contract.getInstallments().remove(installmentIndex);
        contract.getInstallments().add(installmentIndex, installment);

        return contract;
    }
}
