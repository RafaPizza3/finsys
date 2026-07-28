package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.ActiveContractException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.DeleteContractUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractDeleteValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteContractUseCaseImpl implements DeleteContractUseCase {
    private final ContractGateway contractGateway;

    private final ContractDeleteValidation contractDeleteValidation;

    @Override
    public void execute(String id) throws ActiveContractException, ContractNotFoundException {
        ContractDomain domain = contractGateway.findById(id);
        contractDeleteValidation.validate(domain, id);
        contractGateway.delete(id);
    }
}
