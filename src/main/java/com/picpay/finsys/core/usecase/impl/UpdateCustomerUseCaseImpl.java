package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.UpdateCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {
    private final CustomerGateway customerGateway;


    @Override
    public CustomerDomain execute(String id, CustomerDomain customer) throws CustomerNotFoundException {
        CustomerDomain dbCustomer = customerGateway.findById(id);
        if(dbCustomer == null) {
            throw new CustomerNotFoundException(id);
        }

        String name = customer.getName();
        String document = customer.getDocument();
        String email = customer.getEmail();
        LocalDateTime birthDate = customer.getBirthDate();

        LocalDateTime createdAt = LocalDateTime.now();
        CustomerStatus status = CustomerStatus.ACTIVE;

        CustomerDomain domain = CustomerDomain.builder()
                .id(id)
                .name(name)
                .document(document)
                .createdAt(createdAt)
                .status(status)
                .email(email)
                .birthDate(birthDate)
                .build();

        return customerGateway.update(domain);
    }
}
