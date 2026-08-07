package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.DuplicatedEmailException;
import com.picpay.finsys.core.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerEmailValidation {
    private final CustomerGateway customerGateway;

    public void validate(String email) {
        if(customerGateway.customerExistsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }
}
