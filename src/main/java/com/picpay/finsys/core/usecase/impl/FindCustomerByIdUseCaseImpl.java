package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.FindCustomerByIdUseCase;
import com.picpay.finsys.core.usecase.impl.validation.CustomerExistenceValidation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCustomerByIdUseCaseImpl implements FindCustomerByIdUseCase {
    private final CustomerGateway customerGateway;

    private final CustomerExistenceValidation customerExistenceValidation;

    @Override
    @SneakyThrows
    public CustomerDomain execute(String id) {
        CustomerDomain domain = customerGateway.findById(id);
        customerExistenceValidation.validate(id);

        return domain;
    }
}
