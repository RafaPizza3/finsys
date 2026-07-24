package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.ActiveContractException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractDeleteValidation {
    private final ContractExistenceValidation contractExistenceValidation;

    @SneakyThrows
    public void validate(ContractDomain contract, String id) {
        contractExistenceValidation.validate(id);

        if(contract.getStatus() == ContractStatus.ACTIVE) {
            throw new ActiveContractException();
        }
    }
}
