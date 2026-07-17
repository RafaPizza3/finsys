package com.picpay.finsys.dataprovider.repository;

import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.dataprovider.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
    Page<CustomerEntity> findAllByStatus(CustomerStatus status, Pageable page);
}
