package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAllContractByCustomerIdAndStatusUseCase {
    Page<ContractDomain> execute(String customerId, ContractStatus status, Pageable page) throws CustomerNotFoundException;
}
