package com.picpay.finsys.entrypoint.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomerUpdateRequest {
    @Schema(description = "Customer name", example = "Ricardo")
    private String name;

    @Schema(description = "Customer document (CPF)", example = "12345678909")
    private String document;

    @Schema(description = "Customer email", example = "ricardo@email.com")
    private String email;

    @Schema(description = "Customer birth date")
    private LocalDateTime birthDate;
}
