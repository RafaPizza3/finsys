package com.picpay.finsys.dataprovider.entity;

import com.picpay.finsys.core.domain.HistoryDomain;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class InstallmentEntity {
    private String id;

    private Double amount;

    private Double paidAmount;

    private InstallmentStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dueDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime chargeDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime paidDate;

    private Integer daysOverdue;

    private List<HistoryDomain> history;
}
