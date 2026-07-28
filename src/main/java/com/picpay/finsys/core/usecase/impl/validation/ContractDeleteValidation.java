package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.ActiveContractException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractDeleteValidation {
    private final ContractExistenceValidation contractExistenceValidation;

    public void validate(ContractDomain contract, String id) throws ActiveContractException, ContractNotFoundException {
        contractExistenceValidation.validate(id);

        if(contract.getStatus() == ContractStatus.ACTIVE) {
            throw new ActiveContractException();
        }
    }
}
