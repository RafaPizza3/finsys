package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.ContractDomain;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContractUpdateRequestValidation {
    @SneakyThrows
    public void validate(ContractDomain request) {
        if (request.getCustomerId() == null && request.getRequestedAmount() == null && request.getPeriod() == null) {
            throw new BadRequestException("at least 1 value must be requested");
        }
    }
}
