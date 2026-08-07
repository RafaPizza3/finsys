package com.picpay.finsys.dataprovider.repository;

import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.dataprovider.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByEmail(String email);
    Page<CustomerEntity> findAllByStatus(CustomerStatus status, Pageable page);
    Boolean existsCustomerEntityByEmail(String email);
}
