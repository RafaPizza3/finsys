package com.picpay.finsys.dataprovider.repository;

import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.dataprovider.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
    List<CustomerEntity> findAllByStatus(CustomerStatus status);
}
