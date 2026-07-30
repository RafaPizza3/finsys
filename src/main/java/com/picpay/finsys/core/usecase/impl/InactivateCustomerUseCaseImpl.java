package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.CustomerHasContractException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.InactivateCustomerUseCase;
import com.picpay.finsys.core.usecase.impl.validation.CustomerActiveContractsValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerExistenceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InactivateCustomerUseCaseImpl implements InactivateCustomerUseCase {
    private final CustomerGateway customerGateway;

    private final CustomerExistenceValidation customerExistenceValidation;
    private final CustomerActiveContractsValidation customerActiveContractsValidation;

    @Override
    public CustomerDomain execute(String id) throws CustomerNotFoundException, CustomerHasContractException {
        customerExistenceValidation.validate(id);
        customerActiveContractsValidation.validate(id);

        CustomerDomain customer = customerGateway.findById(id);
        customer.setStatus(CustomerStatus.INACTIVE);

        return customerGateway.update(customer);
    }
}
