package com.picpay.finsys.core.gateway;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;

import java.util.List;

public interface CustomerGateway {
    List<CustomerDomain> findAllByStatus(CustomerStatus status);
    List<CustomerDomain> findAll();
    CustomerDomain findById(String id);
    CustomerDomain insert(CustomerDomain customer);
    CustomerDomain update(CustomerDomain customer);
    void delete(String id);
}
