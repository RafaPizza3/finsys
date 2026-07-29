package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.FindContractInstallmentsUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractExistenceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindContractInstallmentsUseCaseImpl implements FindContractInstallmentsUseCase {
    private final ContractGateway contractGateway;

    private final ContractExistenceValidation contractExistenceValidation;

    @Override
    public Page<InstallmentDomain> execute(String contractId) throws ContractNotFoundException {
        contractExistenceValidation.validate(contractId);

        ContractDomain contract = contractGateway.findById(contractId);

        return new PageImpl<>(contract.getInstallments());
    }
}
