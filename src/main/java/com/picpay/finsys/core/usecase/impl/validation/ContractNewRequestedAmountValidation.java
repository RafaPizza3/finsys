package com.picpay.finsys.core.usecase.impl.validation;

import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContractNewRequestedAmountValidation {
    @SneakyThrows
    public void validate(Double requestedAmount, Double originalRequestedAmount) {
        if (requestedAmount < originalRequestedAmount) {
            throw new BadRequestException("contract requested amount must be at least 1000");
        }
    }
}
