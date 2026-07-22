package com.picpay.finsys.core.domain;

import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ContractDomain {
    private String id;
    private String customerId;
    private Double requestedAmount;
    private Double totalAmount;
    private Double monthlyInterestRate;
    private Integer period;
    private Double installmentAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ContractStatus status;
    private List<InstallmentDomain> installments;
}
