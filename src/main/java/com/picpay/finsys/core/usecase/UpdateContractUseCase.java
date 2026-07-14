package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;

public interface UpdateContractUseCase {
    ContractDomain execute(String id, ContractDomain contract) throws ContractNotFoundException;
}
