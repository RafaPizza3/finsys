package com.picpay.finsys.entrypoint.dto.response;

import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomerResponse {
    private String id;
    private String name;
    private String document;
    private LocalDateTime createdAt;
    private CustomerStatus status;
    private String email;
    private AddressResponse address;
}
