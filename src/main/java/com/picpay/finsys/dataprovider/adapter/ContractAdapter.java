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
    public List<ContractDomain> findAllByStatus(ContractStatus status) {
        return contractRepository.findAllByStatus(status)
                .stream()
                .map(contractMapper::toDomain)
                .toList();
    }

    @Override
    public List<ContractDomain> findAll() {
        return contractRepository.findAll()
                .stream()
                .map(contractMapper::toDomain)
                .toList();
    }

    @Override
    public ContractDomain findById(String id) {
        return contractMapper.toDomain(
                contractRepository.findById(id).get()
        );
    }

    @Override
    public ContractDomain insert(ContractDomain contract) {
        return contractMapper.toDomain(contractRepository.insert(
                contractMapper.toEntity(contract)
        ));
    }

    @Override
    public ContractDomain update(ContractDomain contract) {
        return contractMapper.toDomain(contractRepository.save(
                contractMapper.toEntity(contract)
        ));
    }

    @Override
    public void delete(String id) {
        contractRepository.deleteById(id);
    }
}
