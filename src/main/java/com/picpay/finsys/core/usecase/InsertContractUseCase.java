package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;

public interface InsertContractUseCase {
    ContractDomain execute(ContractDomain contract);
}
