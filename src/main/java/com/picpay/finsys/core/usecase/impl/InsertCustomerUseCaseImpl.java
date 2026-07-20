package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.AddressDomain;
import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.CustomerTooYoungException;
import com.picpay.finsys.core.exception.InvalidZipCodeException;
import com.picpay.finsys.core.gateway.AddressGateway;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.InsertCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InsertCustomerUseCaseImpl implements InsertCustomerUseCase {
    private final CustomerGateway customerGateway;
    private final AddressGateway addressGateway;

    @Override
    public CustomerDomain execute(CustomerDomain customer, String zipCode) throws CustomerTooYoungException, InvalidZipCodeException {
        String name = customer.getName();
        String document = customer.getDocument();
        String email = customer.getEmail();
        LocalDateTime birthDate = customer.getBirthDate();
        AddressDomain address = addressGateway.getAdressByZipCode(zipCode);

        verifyAddress(address, zipCode);

        LocalDateTime createdAt = LocalDateTime.now();
        CustomerStatus status = CustomerStatus.ACTIVE;

        verifyCustomerAge(birthDate);

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

    private void verifyCustomerAge(LocalDateTime birthDate) throws CustomerTooYoungException {
        if (birthDate.plusYears(18).isAfter(LocalDateTime.now())) {
            throw new CustomerTooYoungException();
        }
    }

    private void verifyAddress(AddressDomain address, String zipCode) throws InvalidZipCodeException {
        if (address.getAddress() == null) {
            throw new InvalidZipCodeException(zipCode);
        }
    }
}
