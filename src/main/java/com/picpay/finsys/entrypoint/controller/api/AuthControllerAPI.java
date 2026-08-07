package com.picpay.finsys.entrypoint.controller.api;

import com.picpay.finsys.core.exception.CustomerTooYoungException;
import com.picpay.finsys.core.exception.InvalidPasswordException;
import com.picpay.finsys.core.exception.InvalidZipCodeException;
import com.picpay.finsys.entrypoint.dto.request.CustomerRequest;
import com.picpay.finsys.entrypoint.dto.request.LoginRequest;
import com.picpay.finsys.entrypoint.dto.response.LoginResponse;
import com.picpay.finsys.entrypoint.dto.response.SignupResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth", description = "API for authentication")
public interface AuthControllerAPI {
    @Operation(
            summary = "Creates a user",
            description = "Returns created when successful"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Something in user info went wrong"
            )
        }
    )
    public SignupResponse logon(CustomerRequest request) throws InvalidZipCodeException, InvalidPasswordException, CustomerTooYoungException;

    @Operation(
            summary = "Makes login and create a section",
            description = "Returns auth token when successful"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login succeed"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            )
    }
    )
    public LoginResponse login(LoginRequest request);
}
