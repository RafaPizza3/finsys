package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.DeleteContractUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractDeleteValidation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteContractUseCaseImpl implements DeleteContractUseCase {
    private final ContractGateway contractGateway;

    private final ContractDeleteValidation contractDeleteValidation;

    @Override
    @SneakyThrows
    public void execute(String id) {
        ContractDomain domain = contractGateway.findById(id);
        contractDeleteValidation.validate(domain, id);
        contractGateway.delete(id);
    }
}
