package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerExistenceValidation {
    private final CustomerGateway customerGateway;

    public void validate(String customerId) throws CustomerNotFoundException {
        CustomerDomain customer = customerGateway.findById(customerId);

        if(customer == null) {
            throw new CustomerNotFoundException(customerId);
        }
    }
}
