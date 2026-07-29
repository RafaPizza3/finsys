package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.CustomerHasContractException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;

public interface InactivateCustomerUseCase {
    CustomerDomain execute(String id) throws CustomerNotFoundException, CustomerHasContractException;
}
