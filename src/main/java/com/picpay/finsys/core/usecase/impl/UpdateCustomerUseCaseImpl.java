package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.UpdateCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {
    private final CustomerGateway customerGateway;


    @Override
    public CustomerDomain execute(String id, CustomerDomain customer) throws BadRequestException {
        verifyCustomer(id);

        verifyRequest(customer);

        CustomerDomain dbCustomer = customerGateway.findById(id);

        if(customer.getName() != null) {
            dbCustomer.setName(customer.getName());
        }

        if(customer.getDocument() != null) {
            dbCustomer.setDocument(customer.getDocument());
        }

        if(customer.getEmail() != null) {
            dbCustomer.setEmail(customer.getEmail());
        }

        if(customer.getBirthDate() != null) {
            dbCustomer.setBirthDate(customer.getBirthDate());
        }

        System.out.println("email que veio: " + customer.getEmail() + "email que foi: " + dbCustomer.getEmail());

        CustomerDomain domain = createObject(
                dbCustomer.getId(),
                dbCustomer.getName(),
                dbCustomer.getDocument(),
                dbCustomer.getCreatedAt(),
                dbCustomer.getStatus(),
                dbCustomer.getEmail(),
                dbCustomer.getBirthDate()
        );

        return customerGateway.update(domain);
    }

    private CustomerDomain createObject(
            String id,
            String name,
            String document,
            LocalDateTime createdAt,
            CustomerStatus status,
            String email,
            LocalDateTime birthDate
    ) {
        return CustomerDomain.builder()
                .id(id)
                .name(name)
                .document(document)
                .createdAt(createdAt)
                .status(status)
                .email(email)
                .birthDate(birthDate)
                .build();
    }

    private void verifyRequest(CustomerDomain request) throws BadRequestException {
        if (
                request.getName() == null
                        && request.getDocument() == null
                        && request.getEmail() == null
                        && request.getBirthDate() == null
        ) {
            throw new BadRequestException("at least 1 value must be requested");
        }
    }

    private void verifyCustomer(String customerId) throws CustomerNotFoundException {
        CustomerDomain customer = customerGateway.findById(customerId);

        if(customer == null) {
            throw new CustomerNotFoundException(customerId);
        }
    }
}
