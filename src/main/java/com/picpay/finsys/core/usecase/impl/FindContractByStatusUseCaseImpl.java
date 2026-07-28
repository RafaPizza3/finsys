package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.FindContractByStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindContractByStatusUseCaseImpl implements FindContractByStatusUseCase {
    private final ContractGateway contractGateway;


    @Override
    public Page<ContractDomain> execute(ContractStatus status, Pageable page) {
        return contractGateway.findAllByStatus(status, page);
    }
}
