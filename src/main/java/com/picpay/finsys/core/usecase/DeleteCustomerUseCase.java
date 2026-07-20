package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.exception.ActiveContractException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;

public interface DeleteCustomerUseCase {
    void execute(String id) throws CustomerNotFoundException, ActiveContractException;
}
