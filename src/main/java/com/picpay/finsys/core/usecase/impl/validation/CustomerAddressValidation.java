package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.AddressDomain;
import com.picpay.finsys.core.exception.InvalidZipCodeException;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressValidation {
    public void validate(AddressDomain address, String zipCode) throws InvalidZipCodeException {
        if (address.getAddress() == null) {
            throw new InvalidZipCodeException(zipCode);
        }
    }
}
