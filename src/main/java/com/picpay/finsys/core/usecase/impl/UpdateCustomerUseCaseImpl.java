package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.AddressDomain;
import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.exception.CustomerTooYoungException;
import com.picpay.finsys.core.exception.InvalidZipCodeException;
import com.picpay.finsys.core.gateway.AddressGateway;
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
    private final AddressGateway addressGateway;

    @Override
    public CustomerDomain execute(String id, CustomerDomain customer, String zipCode) throws BadRequestException, InvalidZipCodeException {
        verifyCustomer(id);

        verifyRequest(customer, zipCode);

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
            verifyCustomerAge(
                    customer.getBirthDate()
            );
            dbCustomer.setBirthDate(customer.getBirthDate());
        }

        if (zipCode != null) {
            AddressDomain address = addressGateway.getAdressByZipCode(zipCode);
            System.out.println(address.getAddress());
            verifyAddress(address, zipCode);
            dbCustomer.setAddress(address);
        }

        System.out.println("email que veio: " + customer.getEmail() + "email que foi: " + dbCustomer.getEmail());

        CustomerDomain domain = createObject(
                dbCustomer.getId(),
                dbCustomer.getName(),
                dbCustomer.getDocument(),
                dbCustomer.getCreatedAt(),
                dbCustomer.getStatus(),
                dbCustomer.getEmail(),
                dbCustomer.getBirthDate(),
                dbCustomer.getAddress()
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
            LocalDateTime birthDate,
            AddressDomain address
    ) {
        return CustomerDomain.builder()
                .id(id)
                .name(name)
                .document(document)
                .createdAt(createdAt)
                .status(status)
                .email(email)
                .birthDate(birthDate)
                .address(address)
                .build();
    }

    private void verifyRequest(CustomerDomain request, String zipCode) throws BadRequestException {
        if (
                request.getName() == null
                        && request.getDocument() == null
                        && request.getEmail() == null
                        && request.getBirthDate() == null
                        && zipCode == null
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
