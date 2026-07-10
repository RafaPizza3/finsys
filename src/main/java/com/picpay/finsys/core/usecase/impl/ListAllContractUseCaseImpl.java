package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.ListAllContractUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListAllContractUseCaseImpl implements ListAllContractUseCase {
    private final ContractGateway contractGateway;

    @Override
    public List<ContractDomain> execute() {
        return contractGateway.findAll();
    }
}
