package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.CustomerTooYoungException;
import com.picpay.finsys.core.exception.InvalidPasswordException;
import com.picpay.finsys.core.exception.InvalidZipCodeException;

public interface CreateCustomerUseCase {
    String execute(CustomerDomain customer, String zipCode, String number, String detail) throws InvalidZipCodeException, CustomerTooYoungException, InvalidPasswordException;
}
