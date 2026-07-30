package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.InstallmentNotFoundException;

public interface FindInstallmentByIdUseCase {
    InstallmentDomain execute(String contractId, String installmentId) throws ContractNotFoundException, InstallmentNotFoundException;
}
