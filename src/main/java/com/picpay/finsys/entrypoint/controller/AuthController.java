package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.CustomerTooYoungException;
import com.picpay.finsys.core.exception.InvalidPasswordException;
import com.picpay.finsys.core.exception.InvalidZipCodeException;
import com.picpay.finsys.core.usecase.CreateCustomerUseCase;
import com.picpay.finsys.core.usecase.CreateSessionUseCase;
import com.picpay.finsys.dataprovider.config.JwtConfig;
import com.picpay.finsys.entrypoint.controller.api.AuthControllerAPI;
import com.picpay.finsys.entrypoint.dto.request.CustomerRequest;
import com.picpay.finsys.entrypoint.dto.request.LoginRequest;
import com.picpay.finsys.entrypoint.dto.response.LoginResponse;
import com.picpay.finsys.entrypoint.dto.response.SignupResponse;
import com.picpay.finsys.entrypoint.mapper.CustomerMapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerAPI {
    private final CreateCustomerUseCase createCustomerUseCase;
    private final CreateSessionUseCase createSessionUseCase;

    private final CustomerMapperDTO customerMapper;
    private final JwtConfig jwtConfig;

    @Override
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse logon(@RequestBody CustomerRequest request)
            throws InvalidZipCodeException, InvalidPasswordException, CustomerTooYoungException {
        CustomerDomain requestDomain = customerMapper.toDomain(request);
        String customerId = createCustomerUseCase.execute(
                requestDomain,
                request.getAddress().getZipCode(),
                request.getAddress().getNumber(),
                request.getAddress().getDetail()
        );

        return SignupResponse.builder().customerId(customerId).build();
    }

    @Override
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        CustomerDomain domain = createSessionUseCase.execute(request.getEmail(), request.getPassword());

        String token = jwtConfig.generateToken(domain);

        return LoginResponse.builder().token(token).build();
    }
}
