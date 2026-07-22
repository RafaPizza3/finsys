package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.ActiveContractException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.DeleteContractUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteContractUseCaseImpl implements DeleteContractUseCase {
    private final ContractGateway contractGateway;

    public void execute(String id) throws ContractNotFoundException, ActiveContractException {
        ContractDomain domain = contractGateway.findById(id);
        verifyContract(domain, id);
        contractGateway.delete(id);
    }

    private void verifyContract(ContractDomain contract, String id) throws ContractNotFoundException, ActiveContractException {
        if(contract == null) {
            throw new ContractNotFoundException(id);
        }

        if(contract.getStatus() == ContractStatus.ACTIVE) {
            throw new ActiveContractException();
        }
    }
}
