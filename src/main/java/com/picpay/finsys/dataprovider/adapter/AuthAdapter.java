package com.picpay.finsys.dataprovider.adapter;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.NotAuthorizedException;
import com.picpay.finsys.core.gateway.AuthGateway;
import com.picpay.finsys.dataprovider.mapper.CustomerMapper;
import com.picpay.finsys.dataprovider.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthAdapter implements AuthGateway {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public String create(CustomerDomain customer) {
        var entity = customerMapper.toEntity(customer);
        return customerRepository.insert(entity).getId();
    }

    @Override
    public CustomerDomain auth(String email, String password) {
        var entity = customerRepository.findByEmail(email).orElseThrow(NotAuthorizedException::new);

        if(!passwordEncoder.matches(password.trim(), entity.getPassword())) {
            throw new NotAuthorizedException();
        }

        return customerMapper.toDomain(entity);
    }
}
