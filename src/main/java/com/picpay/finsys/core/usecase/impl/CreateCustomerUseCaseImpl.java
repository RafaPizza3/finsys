package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.AddressDomain;
import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.CustomerTooYoungException;
import com.picpay.finsys.core.exception.InvalidPasswordException;
import com.picpay.finsys.core.exception.InvalidZipCodeException;
import com.picpay.finsys.core.gateway.AddressGateway;
import com.picpay.finsys.core.gateway.AuthGateway;
import com.picpay.finsys.core.usecase.CreateCustomerUseCase;
import com.picpay.finsys.core.usecase.impl.validation.CustomerAddressValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerAgeValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerEmailValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerPasswordValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {
    private final AuthGateway customerGateway;
    private final AddressGateway addressGateway;

    private final PasswordEncoder passwordEncoder;

    private final CustomerPasswordValidation customerPasswordValidation;
    private final CustomerEmailValidation customerEmailValidation;
    private final CustomerAddressValidation customerAddressValidation;
    private final CustomerAgeValidation customerAgeValidation;

    @Override
    public String execute(CustomerDomain customer, String zipCode, String number, String detail)
            throws InvalidZipCodeException, CustomerTooYoungException, InvalidPasswordException {
        String name = customer.getName();
        String document = customer.getDocument();
        String email = customer.getEmail();
        String password = customer.getPassword();
        LocalDateTime birthDate = customer.getBirthDate();
        AddressDomain address = addressGateway.getAddressByZipCode(zipCode);

        customerPasswordValidation.validate(password);
        customerEmailValidation.validate(email);

        String passwordHash = passwordEncoder.encode(password);

        customerAddressValidation.validate(address, zipCode);

        address.setNumber(number);
        address.setDetail(detail);

        LocalDateTime createdAt = LocalDateTime.now();
        CustomerStatus status = CustomerStatus.ACTIVE;

        System.out.println(address.getNumber());

        customerAgeValidation.validate(birthDate);

        CustomerDomain domain = createObject(name, document, createdAt, status, email, passwordHash, birthDate, address);

        return customerGateway.create(domain);
    }

    private CustomerDomain createObject(
            String name,
            String document,
            LocalDateTime createdAt,
            CustomerStatus status,
            String email,
            String password,
            LocalDateTime birthDate,
            AddressDomain address
    ) {
        return CustomerDomain.builder()
                .name(name)
                .document(document)
                .createdAt(createdAt)
                .status(status)
                .email(email)
                .password(password)
                .birthDate(birthDate)
                .address(address)
                .build();
    }
}
