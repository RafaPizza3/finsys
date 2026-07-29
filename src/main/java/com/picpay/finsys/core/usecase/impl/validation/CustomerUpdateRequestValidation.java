package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.NullUpdateRequestException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class CustomerUpdateRequestValidation {

    public void validate(CustomerDomain request, String zipCode) throws NullUpdateRequestException {
        if (
                request.getName() == null
                        && request.getDocument() == null
                        && request.getEmail() == null
                        && request.getBirthDate() == null
                        && zipCode == null
        ) {
            throw new NullUpdateRequestException();
        }
    }
}
