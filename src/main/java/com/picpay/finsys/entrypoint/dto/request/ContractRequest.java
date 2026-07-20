package com.picpay.finsys.entrypoint.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractRequest {
    @Schema(description = "Customer ID of the customer that is making the contract", example = "1")
    @NotNull(message = "customer ID must not be null")
    private String customerId;

    @Schema(description = "Contract requested amount", example = "5000")
    @NotNull(message = "requested amount must not be null")
    private Double requestedAmount;

    @Schema(description = "Contract period in months")
    @NotNull(message = "period must not be null")
    private Integer period;
}
