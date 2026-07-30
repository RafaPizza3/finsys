package com.picpay.finsys.entrypoint.dto.response;

import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ContractResponse {
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
}
