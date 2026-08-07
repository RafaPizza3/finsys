package com.picpay.finsys.entrypoint.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    @Schema(description = "Customer email", example = "ricardo@email.com")
    @NotNull(message = "Email must not be null")
    private String email;

    @Schema(description = "Customer password")
    @NotNull(message = "Password must not be null")
    private String password;
}
