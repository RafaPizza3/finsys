package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractExistenceValidation {
    private final ContractGateway contractGateway;

    @SneakyThrows
    public void validate(String id) {
        ContractDomain contract = contractGateway.findById(id);
        if(contract == null) {
            throw new ContractNotFoundException(id);
        }
    }
}
