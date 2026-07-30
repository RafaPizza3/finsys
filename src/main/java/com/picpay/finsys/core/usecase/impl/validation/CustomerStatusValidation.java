package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.InactiveCustomerException;
import com.picpay.finsys.core.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerStatusValidation {
    private final CustomerGateway customerGateway;

    public void validate(String id) throws InactiveCustomerException {
        CustomerDomain customer = customerGateway.findById(id);
        if (customer.getStatus() == CustomerStatus.INACTIVE) {
            throw new InactiveCustomerException();
        }
    }
}
