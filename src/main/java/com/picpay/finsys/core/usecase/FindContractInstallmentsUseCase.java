package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import org.springframework.data.domain.Page;

public interface FindContractInstallmentsUseCase {
    Page<InstallmentDomain> execute(String contractId) throws ContractNotFoundException;
}
