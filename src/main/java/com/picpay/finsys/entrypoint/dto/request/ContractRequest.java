package com.picpay.finsys.entrypoint.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractRequest {
    @NotNull(message = "customer ID must not be null")
    private String customerId;

    @NotNull(message = "total amount must not be null")
    private Double totalAmount;

    @NotNull(message = "period must not be null")
    private Integer period;
}
