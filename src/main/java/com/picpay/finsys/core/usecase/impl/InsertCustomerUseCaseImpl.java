package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.AddressDomain;
import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.gateway.AddressGateway;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.InsertCustomerUseCase;
import com.picpay.finsys.core.usecase.impl.validation.CustomerAddressValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerAgeValidation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InsertCustomerUseCaseImpl implements InsertCustomerUseCase {
    private final CustomerGateway customerGateway;
    private final AddressGateway addressGateway;

    private final CustomerAddressValidation customerAddressValidation;
    private final CustomerAgeValidation customerAgeValidation;

    @Override
    @SneakyThrows
    public CustomerDomain execute(CustomerDomain customer, String zipCode) {
        String name = customer.getName();
        String document = customer.getDocument();
        String email = customer.getEmail();
        LocalDateTime birthDate = customer.getBirthDate();
        AddressDomain address = addressGateway.getAdressByZipCode(zipCode);

        customerAddressValidation.validate(address, zipCode);

        LocalDateTime createdAt = LocalDateTime.now();
        CustomerStatus status = CustomerStatus.ACTIVE;

        customerAgeValidation.validate(birthDate);

        CustomerDomain domain = createObject(name, document, createdAt, status, email, birthDate, address);

        return customerGateway.insert(domain);
    }

    private CustomerDomain createObject(
            String name,
            String document,
            LocalDateTime createdAt,
            CustomerStatus status,
            String email,
            LocalDateTime birthDate,
            AddressDomain address
    ) {
        return CustomerDomain.builder()
                .name(name)
                .document(document)
                .createdAt(createdAt)
                .status(status)
                .email(email)
                .birthDate(birthDate)
                .address(address)
                .build();
    }
}
