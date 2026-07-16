package com.picpay.finsys.core.domain;

import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CustomerDomain {
    private String id;
    private String name;
    private String document;
    private LocalDateTime createdAt;
    private CustomerStatus status;
    private String email;
    private LocalDateTime birthDate;
}
