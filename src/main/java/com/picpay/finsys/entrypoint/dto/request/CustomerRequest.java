package com.picpay.finsys.entrypoint.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomerRequest {
    @Schema(description = "Customer name", example = "Ricardo")
    @NotNull(message = "name must not be null")
    private String name;

    @Schema(description = "Customer document (CPF)", example = "12345678909")
    @NotNull(message = "document must not be null")
    private String document;

    @Schema(description = "Customer email", example = "ricardo@email.com")
    @NotNull(message = "email must not be null")
    private String email;

    @Schema(description = "Customer birth date")
    @NotNull(message = "birth date must not be null")
    private LocalDateTime birthDate;

    @Schema(description = "Customer address zip code")
    @NotNull(message = "zip code must not be null")
    private String zipCode;
}
