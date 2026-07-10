package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.UpdateContractUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateContractUseCaseImpl implements UpdateContractUseCase {
    private final ContractGateway contractGateway;

    @Override
    public ContractDomain execute(ContractDomain contract) {
        return contractGateway.update(contract);
    }
}
