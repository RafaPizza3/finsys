package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.FindCustomerByStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindCustomerByStatusUseCaseImpl implements FindCustomerByStatusUseCase {
    private final CustomerGateway customerGateway;

    @Override
    public Page<CustomerDomain> execute(CustomerStatus status, Pageable page) {
        return customerGateway.findAllByStatus(status, page);
    }
}
