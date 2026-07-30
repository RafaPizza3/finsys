package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.InstallmentNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.FindInstallmentByIdUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractExistenceValidation;
import com.picpay.finsys.core.usecase.impl.validation.InstallmentExistenceValidation;
import com.picpay.finsys.core.usecase.impl.validation.InstallmentIdFormatValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindInstallmentByIdUseCaseImpl implements FindInstallmentByIdUseCase {
    private final ContractGateway contractGateway;

    private final InstallmentIdFormatValidation installmentIdFormatValidation;
    private final ContractExistenceValidation contractExistenceValidation;
    private final InstallmentExistenceValidation installmentExistenceValidation;

    @Override
    public InstallmentDomain execute(String contractId, String installmentId) throws ContractNotFoundException, InstallmentNotFoundException {
        installmentIdFormatValidation.validate(installmentId);
        contractExistenceValidation.validate(contractId);
        installmentExistenceValidation.validate(contractId, installmentId);

        ContractDomain contract = contractGateway.findById(contractId);

        return contract.getInstallments().get(Integer.parseInt(installmentId) - 1);
    }
}
