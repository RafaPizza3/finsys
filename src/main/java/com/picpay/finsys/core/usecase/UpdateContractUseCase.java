package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.exception.NewLowerContractPeriodException;
import com.picpay.finsys.core.exception.NewLowerContractRequestedAmountException;
import com.picpay.finsys.core.exception.NullUpdateRequestException;
import org.apache.coyote.BadRequestException;

public interface UpdateContractUseCase {
    ContractDomain execute(String id, ContractDomain contract) throws NullUpdateRequestException, ContractNotFoundException, NewLowerContractPeriodException, CustomerNotFoundException, NewLowerContractRequestedAmountException;
}
