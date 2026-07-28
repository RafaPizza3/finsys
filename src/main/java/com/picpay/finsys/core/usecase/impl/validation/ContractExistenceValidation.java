package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractExistenceValidation {
    private final ContractGateway contractGateway;

    public void validate(String id) throws ContractNotFoundException {
        ContractDomain contract = contractGateway.findById(id);
        if(contract == null) {
            throw new ContractNotFoundException(id);
        }
    }
}
