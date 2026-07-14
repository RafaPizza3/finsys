package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.CustomerNotFoundException;

public interface FindCustomerByIdUseCase {
    CustomerDomain execute(String id) throws CustomerNotFoundException;
}
