package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.ActiveCustomerException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerDeleteValidation {
    private final CustomerExistenceValidation customerExistenceValidation;

    public void validate(CustomerDomain customer) throws CustomerNotFoundException, ActiveCustomerException {
        customerExistenceValidation.validate(customer.getId());

        if(customer.getStatus() == CustomerStatus.ACTIVE) {
            throw new ActiveCustomerException();
        }
    }
}
