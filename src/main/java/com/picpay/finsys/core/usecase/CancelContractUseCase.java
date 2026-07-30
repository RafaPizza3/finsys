package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.CanceledContractException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.ContractWithPaidInstallmentException;
import com.picpay.finsys.core.exception.FinishedContractException;

public interface CancelContractUseCase {
    ContractDomain execute(String id) throws CanceledContractException, FinishedContractException, ContractNotFoundException, ContractWithPaidInstallmentException;
}
