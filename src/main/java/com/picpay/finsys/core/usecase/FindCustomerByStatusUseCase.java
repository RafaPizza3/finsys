package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;

import java.util.List;

public interface FindCustomerByStatusUseCase {
    List<CustomerDomain> execute(CustomerStatus status);
}
