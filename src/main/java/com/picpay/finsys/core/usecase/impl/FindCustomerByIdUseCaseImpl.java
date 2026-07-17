package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.FindCustomerByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCustomerByIdUseCaseImpl implements FindCustomerByIdUseCase {
    private final CustomerGateway customerGateway;

    @Override
    public CustomerDomain execute(String id) throws CustomerNotFoundException {
        CustomerDomain domain = customerGateway.findById(id);
        if(domain == null) {
            throw new CustomerNotFoundException(id);
        }

        return domain;
    }
}
