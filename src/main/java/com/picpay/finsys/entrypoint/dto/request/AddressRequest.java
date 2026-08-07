package com.picpay.finsys.entrypoint.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class AddressRequest {
    @Schema(description = "Customer residence zip code")
    private String zipCode;

    @Schema(description = "Customer address number", example = "45")
    private String number;

    @Schema(description = "Customer additional address details", example = "Blue walls")
    private String detail;
}
