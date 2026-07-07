package com.picpay.finsys.core.domain;

import com.picpay.finsys.core.domain.enumeration.ContractStatusEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ContractDomain {
    private String id;
    private String customerId;
    private Double totalAmount;
    private Integer period;
    private Double installmentAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ContractStatusEnum status;
    private List<InstallmentDomain> installments;
}
