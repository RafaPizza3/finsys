package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.CustomerNotFoundException;

public interface UpdateCustomerUseCase {
    CustomerDomain execute (String id, CustomerDomain customer) throws CustomerNotFoundException;
}
