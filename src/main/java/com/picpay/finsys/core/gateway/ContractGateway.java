package com.picpay.finsys.core.gateway;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;

import java.util.List;

public interface ContractGateway {
    List<ContractDomain> findAllByStatus(ContractStatus status);
    List<ContractDomain> findAll();
    ContractDomain findById(String id);
    ContractDomain insert(ContractDomain contract);
    ContractDomain update(ContractDomain contract);
    void delete(String id);
}
