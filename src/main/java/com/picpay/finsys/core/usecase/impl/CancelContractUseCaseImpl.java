package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.CanceledContractException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.ContractWithPaidInstallmentException;
import com.picpay.finsys.core.exception.FinishedContractException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.CancelContractUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractExistenceValidation;
import com.picpay.finsys.core.usecase.impl.validation.ContractHasPaidInstallmentValidation;
import com.picpay.finsys.core.usecase.impl.validation.ContractStatusValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelContractUseCaseImpl implements CancelContractUseCase {
    private final ContractGateway contractGateway;

    private final ContractExistenceValidation contractExistenceValidation;
    private final ContractStatusValidation contractStatusValidation;
    private final ContractHasPaidInstallmentValidation contractHasPaidInstallmentValidation;

    public ContractDomain execute(String id) throws CanceledContractException, FinishedContractException, ContractNotFoundException, ContractWithPaidInstallmentException {
        ContractDomain contract = contractGateway.findById(id);

        contractExistenceValidation.validate(id);
        contractStatusValidation.validate(contract);
        contractHasPaidInstallmentValidation.validate(contract);

        contract.setStatus(ContractStatus.CANCELED);

        return cancelInstallments(contract);
    }

    ContractDomain cancelInstallments(ContractDomain contract) {
        for (int i = 0; i == contract.getInstallments().size(); i++) {
            InstallmentDomain installment = contract.getInstallments().get(i);
            installment.setStatus(InstallmentStatus.CANCELED);

            contract.getInstallments().remove(i);
            contract.getInstallments().add(i, installment);
        }

        return contract;
    }
}
