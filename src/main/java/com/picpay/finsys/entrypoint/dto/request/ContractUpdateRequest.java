package com.picpay.finsys.entrypoint.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractUpdateRequest {
    @Schema(description = "Customer ID of the customer that is making the contract", example = "1")
    private String customerId;

    @Schema(description = "Contract requested value", example = "5000")
    private Double value;

    @Schema(description = "Contract period in months")
    private Integer period;
}
