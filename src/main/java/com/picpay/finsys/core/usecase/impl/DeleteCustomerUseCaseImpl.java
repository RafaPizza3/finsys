package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.DeleteCustomerUseCase;
import com.picpay.finsys.core.usecase.impl.validation.CustomerActiveContractsValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerExistenceValidation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCustomerUseCaseImpl implements DeleteCustomerUseCase {
    private final CustomerGateway customerGateway;
    private final ContractGateway contractGateway;

    private final CustomerExistenceValidation customerExistenceValidation;
    private final CustomerActiveContractsValidation customerActiveContractsValidation;

    @Override
    @SneakyThrows
    public void execute(String id) {
        customerExistenceValidation.validate(id);
        customerActiveContractsValidation.validate(id);
        customerGateway.delete(id);
    }
}
