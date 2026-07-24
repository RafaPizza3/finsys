package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;

public interface UpdateContractUseCase {
    ContractDomain execute(String id, ContractDomain contract);
}
