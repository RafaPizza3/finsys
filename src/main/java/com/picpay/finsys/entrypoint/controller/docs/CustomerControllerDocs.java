package com.picpay.finsys.entrypoint.controller.docs;

import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.entrypoint.dto.request.CustomerRequest;
import com.picpay.finsys.entrypoint.dto.request.CustomerUpdateRequest;
import com.picpay.finsys.entrypoint.dto.response.CustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Customers", description = "API for customers management")
public interface CustomerControllerDocs {
    @Operation(
            summary = "Gets all customers with that status",
            description = "Returns a Pageable item with customers"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pageable list returned with success"
            )
    }
    )
    Page<CustomerResponse> findAllByStatus(CustomerStatus status, Pageable page);

    @Operation(
            summary = "Gets all customers",
            description = "Returns a Pageable item with customers"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pageable list returned with success"
            )
    }
    )
    Page<CustomerResponse> findAll(Pageable page);

    @Operation(
            summary = "Gets a contract by ID",
            description = "Returns a customer"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "customer found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "customer not found"
            )
    }
    )
    CustomerResponse findById(String id);

    @Operation(
            summary = "Inserts a customer",
            description = "Returns the created customer"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Some info in request went wrong"
            )
    }
    )
    CustomerResponse insert(CustomerRequest request);

    @Operation(
            summary = "Updates a customer",
            description = "Returns the updated customer"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Some info in request went wrong"
            )
    }
    )
    CustomerResponse update(String id, CustomerUpdateRequest request);

    @Operation(
            summary = "Deletes a customer"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Customer deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Customer not found"
            )
    }
    )
    void delete(String id);
}
