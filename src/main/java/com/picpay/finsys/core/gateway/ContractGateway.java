package com.picpay.finsys.core.gateway;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ContractGateway {
    Page<ContractDomain> findAllByCustomerIdAndStatus(String customerId, ContractStatus status, Pageable page);
    Page<ContractDomain> findAllByStatus(ContractStatus status, Pageable page);
    Page<ContractDomain> findAll(Pageable page);
    List<ContractDomain> findAllIds();
    ContractDomain findById(String id);
    ContractDomain insert(ContractDomain contract);
    ContractDomain update(ContractDomain contract);
    void delete(String id);
    Integer countActiveContractsByCustomerId(String customerId, ContractStatus status);
}
