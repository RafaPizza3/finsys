package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.DeleteContractUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteContractUseCaseImpl implements DeleteContractUseCase {
    private final ContractGateway contractGateway;

    public void execute(String id) throws ContractNotFoundException {
        ContractDomain domain = contractGateway.findById(id);
        if(domain == null) {
            throw new ContractNotFoundException(id);
        }
        contractGateway.delete(id);
    }
}
