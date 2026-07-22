package com.picpay.finsys.core.usecase.impl.common;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;

public class ChangeContract {

    public void verifyCustomer(String customerId, CustomerGateway customerGateway) throws CustomerNotFoundException {
        CustomerDomain customer = customerGateway.findById(customerId);

        if(customer == null) {
            throw new CustomerNotFoundException(customerId);
        }
    }
}
