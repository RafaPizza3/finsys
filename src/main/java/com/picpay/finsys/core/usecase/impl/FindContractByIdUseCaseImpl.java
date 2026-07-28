package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.FindContractByIdUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractExistenceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindContractByIdUseCaseImpl implements FindContractByIdUseCase {
    private final ContractGateway contractGateway;

    private final ContractExistenceValidation contractExistenceValidation;

    @Override
    public ContractDomain execute(String id) throws ContractNotFoundException {
        ContractDomain domain = contractGateway.findById(id);
        contractExistenceValidation.validate(id);

        return domain;
    }
}
