package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.InsertCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InsertCustomerUseCaseImpl implements InsertCustomerUseCase {
    private final CustomerGateway customerGateway;

    @Override
    public CustomerDomain execute(CustomerDomain customer) {
        String name = customer.getName();
        String document = customer.getDocument();
        String email = customer.getEmail();
        LocalDateTime birthDate = customer.getBirthDate();

        LocalDateTime createdAt = LocalDateTime.now();
        CustomerStatus status = CustomerStatus.ACTIVE;

        CustomerDomain domain = CustomerDomain.builder()
                .name(name)
                .document(document)
                .createdAt(createdAt)
                .status(status)
                .email(email)
                .birthDate(birthDate)
                .build();
        return customerGateway.insert(domain);
    }
}
