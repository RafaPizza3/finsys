package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAllCustomerUseCase {
    Page<CustomerDomain> execute(Pageable page);
}
