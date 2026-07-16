package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.CustomerNotFoundException;

public interface InsertContractUseCase {
    ContractDomain execute(ContractDomain contract) throws CustomerNotFoundException;
}
