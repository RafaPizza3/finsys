package com.picpay.finsys.entrypoint.dto.response;

import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class InstallmentResponse {
    private String id;
    private Double amount;
    private Double paidAmount;
    private InstallmentStatus status;
    private LocalDateTime dueDate;
    private LocalDateTime paidDate;
}
