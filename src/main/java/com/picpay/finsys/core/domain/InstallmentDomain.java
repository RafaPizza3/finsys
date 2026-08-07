package com.picpay.finsys.core.domain;

import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class InstallmentDomain {
    private String id;
    private Double amount;
    private Double paidAmount;
    private InstallmentStatus status;
    private LocalDateTime dueDate;
    private LocalDateTime chargeDate;
    private LocalDateTime paidDate;
    private Long daysOverdue;
    List<HistoryDomain> history;
}
