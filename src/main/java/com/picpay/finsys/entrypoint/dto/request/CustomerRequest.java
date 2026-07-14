package com.picpay.finsys.entrypoint.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomerRequest {
    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "document must not be null")
    private String document;

    @NotNull(message = "email must not be null")
    private String email;

    @NotNull(message = "bith date must not be null")
    private LocalDateTime birthDate;
}
