package com.picpay.finsys.dataprovider.adapter;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.dataprovider.mapper.ContractMapper;
import com.picpay.finsys.dataprovider.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractAdapter implements ContractGateway {
    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    @Override
    public List<ContractDomain> listAllActive(ContractStatus status) {
        return contractRepository.findAllByStatus(status)
                .stream()
                .map(contractMapper::toDomain)
                .toList();
    }

    @Override
    public void insert(ContractDomain contract) {
        contractRepository.insert(contractMapper.toEntity(contract));
    }
}
