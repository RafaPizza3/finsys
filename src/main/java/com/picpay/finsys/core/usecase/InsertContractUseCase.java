package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import org.apache.coyote.BadRequestException;

public interface InsertContractUseCase {
    ContractDomain execute(ContractDomain contract) throws BadRequestException;
}
