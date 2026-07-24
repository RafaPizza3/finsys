package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;

public interface FindContractByIdUseCase {
    ContractDomain execute(String id);
}
