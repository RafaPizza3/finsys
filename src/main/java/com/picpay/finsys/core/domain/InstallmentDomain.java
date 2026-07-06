package com.picpay.finsys.core.domain;

import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class InstallmentDomain {
    private String id;
    private double amount;
    private InstallmentStatus status;
    private LocalDateTime dueDate;
    private LocalDateTime paidDate;
}
