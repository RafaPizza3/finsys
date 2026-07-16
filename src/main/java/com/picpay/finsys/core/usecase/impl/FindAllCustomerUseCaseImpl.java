package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.FindAllCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllCustomerUseCaseImpl implements FindAllCustomerUseCase {
    private final CustomerGateway customerGateway;


    @Override
    public Page<CustomerDomain> execute(Pageable page) {
        return customerGateway.findAll(page);
    }
}
