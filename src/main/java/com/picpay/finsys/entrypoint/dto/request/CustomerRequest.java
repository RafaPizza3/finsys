package com.picpay.finsys.entrypoint.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jdk.jfr.ContentType;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomerRequest {
    @Schema(description = "Customer name", example = "Ricardo")
    @NotNull(message = "name must not be null")
    private String name;

    @Schema(description = "Customer document (CPF)", example = "12345678909")
    @NotNull(message = "document must not be null")
    @CPF(message = "the requested document is not valid")
    private String document;

    @Schema(description = "Customer email", example = "ricardo@email.com")
    @NotNull(message = "email must not be null")
    @Email(message = "the requested email is not valid")
    private String email;

    @Schema(description = "Customer password", example = "aBc123")
    @NotNull(message = "password must not be null")
    private String password;

    @Schema(description = "Customer birth date")
    @NotNull(message = "birth date must not be null")
    private LocalDateTime birthDate;

    @Schema(description = "Customer address info")
    private AddressRequest address;
}
