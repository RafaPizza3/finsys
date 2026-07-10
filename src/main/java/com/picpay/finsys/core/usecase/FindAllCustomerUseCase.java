package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;

import java.util.List;

public interface FindAllCustomerUseCase {
    List<CustomerDomain> execute();
}
