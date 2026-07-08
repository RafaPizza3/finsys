package com.picpay.finsys.dataprovider.entity;

import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Document(collection = "contract")
public class ContractEntity {
    @MongoId
    private String id;

    private String customerId;

    private Double totalAmount;

    private Integer period;

    private Double installmentAmount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    private ContractStatus status;

    private List<InstallmentEntity> installments;
}
