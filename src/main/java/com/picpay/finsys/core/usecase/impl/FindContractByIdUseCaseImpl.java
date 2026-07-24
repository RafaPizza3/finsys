package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.FindContractByIdUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractExistenceValidation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindContractByIdUseCaseImpl implements FindContractByIdUseCase {
    private final ContractGateway contractGateway;

    private final ContractExistenceValidation contractExistenceValidation;

    @Override
    @SneakyThrows
    public ContractDomain execute(String id) {
        ContractDomain domain = contractGateway.findById(id);
        contractExistenceValidation.validate(id);

        return domain;
    }
}
