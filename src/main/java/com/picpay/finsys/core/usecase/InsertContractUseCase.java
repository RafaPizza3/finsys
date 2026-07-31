package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.ContractLowPeriodException;
import com.picpay.finsys.core.exception.ContractLowRequestedAmountException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.exception.InactiveCustomerException;
import org.apache.coyote.BadRequestException;

public interface InsertContractUseCase {
    ContractDomain execute(ContractDomain contract) throws CustomerNotFoundException, ContractLowRequestedAmountException, InactiveCustomerException, ContractLowPeriodException;
}
