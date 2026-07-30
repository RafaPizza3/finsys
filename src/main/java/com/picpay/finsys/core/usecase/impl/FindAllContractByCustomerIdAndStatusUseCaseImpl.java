package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.FindAllContractByCustomerIdAndStatusUseCase;
import com.picpay.finsys.core.usecase.impl.validation.CustomerExistenceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllContractByCustomerIdAndStatusUseCaseImpl implements FindAllContractByCustomerIdAndStatusUseCase {
    private final ContractGateway contractGateway;

    private final CustomerExistenceValidation customerExistenceValidation;
    @Override
    public Page<ContractDomain> execute(String customerId, ContractStatus status, Pageable page) throws CustomerNotFoundException {
        customerExistenceValidation.validate(customerId);

        return contractGateway.findAllByCustomerIdAndStatus(customerId, status, page);
    }
}
