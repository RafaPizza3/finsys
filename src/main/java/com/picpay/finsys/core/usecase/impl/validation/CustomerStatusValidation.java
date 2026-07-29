package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.ActiveCustomerException;
import org.springframework.stereotype.Component;

@Component
public class CustomerStatusValidation {
    public void validate(CustomerDomain customer) throws ActiveCustomerException {
        if (customer.getStatus() == CustomerStatus.ACTIVE) {
            throw new ActiveCustomerException();
        }
    }
}
