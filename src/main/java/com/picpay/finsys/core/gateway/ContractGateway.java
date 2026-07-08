package com.picpay.finsys.core.gateway;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;

import java.util.List;

public interface ContractGateway {
    List<ContractDomain> listAllActive(ContractStatus status);
    void insert(ContractDomain contract);
}
