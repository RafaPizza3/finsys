package com.picpay.finsys.core.usecase.impl.common;

import com.picpay.finsys.core.domain.AddressDomain;
import com.picpay.finsys.core.exception.CustomerTooYoungException;
import com.picpay.finsys.core.exception.InvalidZipCodeException;

import java.time.LocalDateTime;

public class ChangeCustomer {
    Integer majorityAge = 18;

    public void verifyCustomerAge(LocalDateTime birthDate) throws CustomerTooYoungException {
        if (birthDate.plusYears(this.majorityAge).isAfter(LocalDateTime.now())) {
            throw new CustomerTooYoungException();
        }
    }

    public void verifyAddress(AddressDomain address, String zipCode) throws InvalidZipCodeException {
        if (address.getAddress() == null) {
            throw new InvalidZipCodeException(zipCode);
        }
    }
}
