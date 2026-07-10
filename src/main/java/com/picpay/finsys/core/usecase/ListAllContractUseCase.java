package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;

import java.util.List;

public interface ListAllContractUseCase {
    List<ContractDomain> execute();
}
