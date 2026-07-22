package com.picpay.finsys.core.usecase.impl.validation;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContractNewRequestedAmountValidation {
    public void validate(Double requestedAmount, Double originalRequestedAmount) throws BadRequestException {
        if (requestedAmount < originalRequestedAmount) {
            throw new BadRequestException("contract requested amount must be at least 1000");
        }
    }
}
