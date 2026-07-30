package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.CustomerTooYoungException;
import com.picpay.finsys.core.exception.InvalidDocumentException;
import com.picpay.finsys.core.exception.InvalidEmailException;
import com.picpay.finsys.core.exception.InvalidZipCodeException;

public interface InsertCustomerUseCase {
    CustomerDomain execute(CustomerDomain customer, String zipCode) throws InvalidZipCodeException, CustomerTooYoungException, InvalidDocumentException, InvalidEmailException;
}
