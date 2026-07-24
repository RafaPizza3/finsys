package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;

public interface InsertCustomerUseCase {
    CustomerDomain execute(CustomerDomain customer, String zipCode);
}
