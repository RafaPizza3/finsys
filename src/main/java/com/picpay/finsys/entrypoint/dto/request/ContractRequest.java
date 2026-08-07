package com.picpay.finsys.entrypoint.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractRequest {
    @Schema(description = "Contract requested amount", example = "5000")
    @NotNull(message = "requested amount must not be null")
    private Double requestedAmount;

    @Schema(description = "Contract period in months")
    @NotNull(message = "period must not be null")
    @Min(value = 6, message = "the minimum periods is 6")
    private Integer period;

    @Schema(description = "Months until charge the first installment")
    @Max(value = 6, message = "the limit of months until charge is 6")
    private Integer monthsUntilCharge;
}
