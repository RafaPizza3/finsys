package com.picpay.finsys.core.domain;

import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class InstallmentDomain {
    private String id;
    private Double amount;
    private InstallmentStatus status;
    private LocalDateTime dueDate;
    private LocalDateTime paidDate;
}
