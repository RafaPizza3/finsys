package com.picpay.finsys.dataprovider.repository;

import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.dataprovider.entity.ContractEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends MongoRepository<ContractEntity, String> {
    List<ContractEntity> findAllByStatus(ContractStatus status);
}
