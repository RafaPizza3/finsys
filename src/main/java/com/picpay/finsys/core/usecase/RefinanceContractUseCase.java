package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.CanceledContractException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.FinishedContractException;

public interface RefinanceContractUseCase {
    ContractDomain execute(String contractId, Integer period, Integer monthsUntilCharge) throws ContractNotFoundException, CanceledContractException, FinishedContractException;
}
