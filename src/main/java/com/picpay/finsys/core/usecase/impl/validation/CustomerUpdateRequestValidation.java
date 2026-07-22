package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.CustomerDomain;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class CustomerUpdateRequestValidation {
    public void validate(CustomerDomain request, String zipCode) throws BadRequestException {
        if (
                request.getName() == null
                        && request.getDocument() == null
                        && request.getEmail() == null
                        && request.getBirthDate() == null
                        && zipCode == null
        ) {
            throw new BadRequestException("at least 1 value must be requested");
        }
    }
}
