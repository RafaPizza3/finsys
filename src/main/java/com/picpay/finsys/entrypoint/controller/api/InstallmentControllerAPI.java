package com.picpay.finsys.entrypoint.controller.api;

import com.picpay.finsys.core.exception.CanceledInstallmentException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.ExceededInstallmentAmountInPaymentException;
import com.picpay.finsys.core.exception.InstallmentNotFoundException;
import com.picpay.finsys.core.exception.InstallmentPaymentPriorityException;
import com.picpay.finsys.core.exception.PaidInstallmentException;
import com.picpay.finsys.entrypoint.dto.request.InstallmentPaymentRequest;
import com.picpay.finsys.entrypoint.dto.response.InstallmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@Tag(name = "Installments", description = "API for installments management")
public interface InstallmentControllerAPI {
    @Operation(
            summary = "Finds all installments by contract id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Installments found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The installments cannot be found"
            )
    })
    Page<InstallmentResponse> findInstallments(String contractId, @PageableDefault(6) Pageable page) throws ContractNotFoundException;

    @Operation(
            summary = "Finds a installment by its contract id an its id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Installment found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The installment cannot be found"
            )
    })
    InstallmentResponse findInstallmentById(String contractId, String installmentId) throws InstallmentNotFoundException, ContractNotFoundException;

    @Operation(
            summary = "Pay a installment"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Installment paid"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Some info in request went wrong"
            )
    })
    InstallmentResponse payInstallment(InstallmentPaymentRequest request) throws InstallmentNotFoundException, ExceededInstallmentAmountInPaymentException, ContractNotFoundException, InstallmentPaymentPriorityException, PaidInstallmentException, CanceledInstallmentException;

}
