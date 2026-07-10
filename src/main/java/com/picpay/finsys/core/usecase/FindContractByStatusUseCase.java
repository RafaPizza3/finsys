package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;

import java.util.List;

public interface FindContractByStatusUseCase {
    List<ContractDomain> execute(ContractStatus status);
}
