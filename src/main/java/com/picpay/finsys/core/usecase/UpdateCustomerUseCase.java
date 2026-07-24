package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;

public interface UpdateCustomerUseCase {
    CustomerDomain execute (String id, CustomerDomain customer, String zipCode);
}
