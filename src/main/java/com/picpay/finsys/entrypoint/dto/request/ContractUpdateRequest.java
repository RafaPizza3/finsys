package com.picpay.finsys.entrypoint.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ContractUpdateRequest {
    @NotNull(message = "id must not be null")
    private String id;

    private ContractRequest request;
}
