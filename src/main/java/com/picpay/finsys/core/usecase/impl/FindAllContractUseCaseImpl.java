package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.FindAllContractUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllContractUseCaseImpl implements FindAllContractUseCase {
    private final ContractGateway contractGateway;

    @Override
    public Page<ContractDomain> execute(Pageable page) {
        return contractGateway.findAll(page);
    }
}
