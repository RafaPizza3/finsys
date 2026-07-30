package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.ContractLowRequestedAmountException;
import com.picpay.finsys.core.exception.NewLowerContractRequestedAmountException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContractNewRequestedAmountValidation {
    public void validate(Double requestedAmount, Double originalRequestedAmount) throws NewLowerContractRequestedAmountException {
        if (requestedAmount < originalRequestedAmount) {
            throw new NewLowerContractRequestedAmountException();
        }
    }
}
