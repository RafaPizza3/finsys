package com.picpay.finsys.dataprovider.adapter;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.dataprovider.entity.ContractEntity;
import com.picpay.finsys.dataprovider.mapper.ContractMapper;
import com.picpay.finsys.dataprovider.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractAdapter implements ContractGateway {
    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    @Override
    public Page<ContractDomain> findAllByStatus(ContractStatus status, Pageable page) {
        Page<ContractEntity> contractPage = contractRepository.findAllByStatus(status, page);
        List<ContractDomain> contractList = contractPage
                .stream()
                .map(contractMapper::toDomain)
                .toList();

        return new PageImpl<>(contractList, page, contractPage.getTotalElements());
    }

    @Override
    public Page<ContractDomain> findAll(Pageable page) {
        Page<ContractEntity> contractPage = contractRepository.findAll(page);
        List<ContractDomain> contractList = contractPage
                .stream()
                .map(contractMapper::toDomain)
                .toList();

        return new PageImpl<>(contractList, page,contractPage.getTotalElements());
    }

    @Override
    public ContractDomain findById(String id) {
        var entity = contractRepository.findById(id).orElse(null);
        return contractMapper.toDomain(entity);
    }

    @Override
    public ContractDomain insert(ContractDomain contract) {
        var entity = contractMapper.toEntity(contract);
        var saved = contractRepository.insert(entity);
        return contractMapper.toDomain(saved);
    }

    @Override
    public ContractDomain update(ContractDomain contract) {
        var entity = contractMapper.toEntity(contract);
        var saved = contractRepository.save(entity);
        return contractMapper.toDomain(saved);
    }

    @Override
    public void delete(String id) {
        contractRepository.deleteById(id);
    }
}
