package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import org.apache.coyote.BadRequestException;

public interface UpdateContractUseCase {
    ContractDomain execute(String id, ContractDomain contract) throws BadRequestException;
}
