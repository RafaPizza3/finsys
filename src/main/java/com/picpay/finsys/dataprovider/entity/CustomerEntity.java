package com.picpay.finsys.dataprovider.entity;

import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@Document(collection = "customer")
public class CustomerEntity {
    @MongoId
    private String id;

    private String name;

    private String document;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    private CustomerStatus status;

    private String email;

    private LocalDateTime birthDate;

    private AddressEntity address;
}
