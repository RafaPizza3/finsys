package com.picpay.finsys.dataprovider.adapter;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.dataprovider.entity.CustomerEntity;
import com.picpay.finsys.dataprovider.mapper.CustomerMapper;
import com.picpay.finsys.dataprovider.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerGateway {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Page<CustomerDomain> findAllByStatus(CustomerStatus status, Pageable page) {
        Page<CustomerEntity> customerPage = customerRepository.findAllByStatus(status, page);
        List<CustomerDomain> customerList = customerPage
                .stream()
                .map(customerMapper::toDomain)
                .toList();

        return new PageImpl<>(customerList, page, customerPage.getTotalElements());
    }

    @Override
    public Page<CustomerDomain> findAll(Pageable page) {
        Page<CustomerEntity> customerPage = customerRepository.findAll(page);
        List<CustomerDomain> customerList = customerPage
                .stream()
                .map(customerMapper::toDomain)
                .toList();

        return new PageImpl<>(customerList, page, customerPage.getTotalElements());
    }

    @Override
    public CustomerDomain findById(String id) {
        var entity = customerRepository.findById(id).orElse(null);
        return customerMapper.toDomain(entity);
    }

    @Override
    public CustomerDomain insert(CustomerDomain customer) {
        var entity = customerMapper.toEntity(customer);
        var saved = customerRepository.insert(entity);
        return customerMapper.toDomain(saved);
    }

    @Override
    public CustomerDomain update(CustomerDomain customer) {
        var entity = customerMapper.toEntity(customer);
        var saved = customerRepository.save(entity);
        return customerMapper.toDomain(saved);
    }

    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }
}
