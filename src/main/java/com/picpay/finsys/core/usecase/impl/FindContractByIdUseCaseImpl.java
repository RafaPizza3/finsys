package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.FindContractByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindContractByIdUseCaseImpl implements FindContractByIdUseCase {
    private final ContractGateway contractGateway;


    @Override
    public ContractDomain execute(String id) {
        return contractGateway.findById(id);
    }
}
