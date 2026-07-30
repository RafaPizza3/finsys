package com.picpay.finsys.entrypoint.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InstallmentPaymentRequest {
    @Schema(description = "Contract ID", example = "abcdefg123")
    private String contractId;

    @Schema(description = "Installment ID", example = "1")
    private String installmentId;

    @Schema(description = "Payment amount", example = "300")
    private Double paymentAmount;
}
