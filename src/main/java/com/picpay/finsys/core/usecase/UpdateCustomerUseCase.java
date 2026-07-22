package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.exception.InvalidZipCodeException;
import org.apache.coyote.BadRequestException;

public interface UpdateCustomerUseCase {
    CustomerDomain execute (String id, CustomerDomain customer, String zipCode) throws BadRequestException, InvalidZipCodeException;
}
