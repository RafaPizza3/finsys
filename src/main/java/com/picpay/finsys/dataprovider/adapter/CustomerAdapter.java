package com.picpay.finsys.dataprovider.adapter;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.dataprovider.mapper.CustomerMapper;
import com.picpay.finsys.dataprovider.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerGateway {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDomain> findAllByStatus(CustomerStatus status) {
        return customerRepository.findAllByStatus(status)
                .stream()
                .map(customerMapper::toDomain)
                .toList();
    }

    @Override
    public List<CustomerDomain> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDomain)
                .toList();
    }

    @Override
    public CustomerDomain findById(String id) {
        var entity = customerRepository.findById(id).get();
        return customerMapper.toDomain(entity);
    }

    @Override
    public CustomerDomain insert(CustomerDomain customer) {
        var entity = customerMapper.toEntity(customer);
        var saved = customerRepository.insert(entity);
        return customerMapper.toDomain(customerRepository.insert(saved));
    }

    @Override
    public CustomerDomain update(CustomerDomain customer) {
        var entity = customerMapper.toEntity(customer);
        var saved = customerRepository.save(entity);
        return customerMapper.toDomain(customerRepository.save(saved));
    }

    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }
}
