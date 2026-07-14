package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.exception.ContractNotFoundException;

public interface DeleteContractUseCase {
    void execute(String id) throws ContractNotFoundException;
}
