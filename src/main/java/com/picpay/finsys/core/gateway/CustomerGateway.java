package com.picpay.finsys.core.gateway;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerGateway {
    Page<CustomerDomain> findAllByStatus(CustomerStatus status, Pageable page);
    Page<CustomerDomain> findAll(Pageable page);
    CustomerDomain findById(String id);
    CustomerDomain findByEmail(String email);
    CustomerDomain insert(CustomerDomain customer);
    CustomerDomain update(CustomerDomain customer);
    void delete(String id);
    Boolean customerExistsByEmail(String email);
}
