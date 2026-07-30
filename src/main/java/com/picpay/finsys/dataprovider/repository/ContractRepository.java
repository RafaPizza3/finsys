package com.picpay.finsys.dataprovider.repository;

import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.dataprovider.entity.ContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends MongoRepository<ContractEntity, String> {
    Page<ContractEntity> findAllByStatus(ContractStatus status, Pageable page);
    Page<ContractEntity> findAllByCustomerIdAndStatus(String customerId, ContractStatus status, Pageable page);
    Integer countContractEntitiesByCustomerIdAndStatus(String customerId, ContractStatus status);
}
