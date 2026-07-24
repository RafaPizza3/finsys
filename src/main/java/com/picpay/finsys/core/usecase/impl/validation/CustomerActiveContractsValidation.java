package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.CustomerHasContractException;
import com.picpay.finsys.core.gateway.ContractGateway;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerActiveContractsValidation {
    private final ContractGateway contractGateway;

    @SneakyThrows
    public void validate(String id) {
        Integer activeContractsAmount = contractGateway.countActiveContractsByCustomerId(id, ContractStatus.ACTIVE);

        if (activeContractsAmount > 0) {
            throw new CustomerHasContractException();
        }
    }
}
