package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.FindContractByStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindContractByStatusUseCaseImpl implements FindContractByStatusUseCase {
    private final ContractGateway contractGateway;


    @Override
    public List<ContractDomain> execute(ContractStatus status) {
        return contractGateway.findAllByStatus(status);
    }
}
