package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.DeleteContractUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteContractUseCaseImpl implements DeleteContractUseCase {
    private final ContractGateway contractGateway;

    public void execute(String id) {
        contractGateway.delete(id);
    }
}
