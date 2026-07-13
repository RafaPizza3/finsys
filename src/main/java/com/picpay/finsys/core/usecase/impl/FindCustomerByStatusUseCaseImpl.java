package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.FindCustomerByStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindCustomerByStatusUseCaseImpl implements FindCustomerByStatusUseCase {
    private final CustomerGateway customerGateway;

    @Override
    public List<CustomerDomain> execute(CustomerStatus status) {
        return customerGateway.findAllByStatus(status);
    }
}
