package com.picpay.finsys.entrypoint.controller.docs;

import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.ActiveContractException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.entrypoint.dto.request.ContractRequest;
import com.picpay.finsys.entrypoint.dto.request.ContractUpdateRequest;
import com.picpay.finsys.entrypoint.dto.response.ContractResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Contracts", description = "API for contracts management")
public interface ContractControllerDocs {
    @Operation(
            summary = "Gets all contracts with that status",
            description = "Returns a Pageable item with contracts"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pageable list returned with success"
            )
        }
    )
    Page<ContractResponse> findAllByStatus(ContractStatus status, Pageable page);

    @Operation(
            summary = "Gets all contracts",
            description = "Returns a Pageable item with contracts"
    )
    @ApiResponses(value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pageable list returned with success"
                    )
            }
    )
    Page<ContractResponse> findAll(Pageable page);

    @Operation(
            summary = "Gets a contract by ID",
            description = "Returns a contract"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Contract not found"
            )
        }
    )
    ContractResponse findById(String id) throws ContractNotFoundException;

    @Operation(
            summary = "Inserts a contract",
            description = "Returns the created contract"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Contract created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Some info in request went wrong"
            )
        }
    )
    ContractResponse insert(ContractRequest request) throws BadRequestException;

    @Operation(
            summary = "Updates a contract",
            description = "Returns the updated contract"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Contract updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Some info in request went wrong"
            )
    }
    )
    ContractResponse update(String id, ContractUpdateRequest request) throws BadRequestException;

    @Operation(
            summary = "Deletes a contract"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Contract deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Contract not found"
            )
        }
    )
    void delete(String id) throws ContractNotFoundException, ActiveContractException;
}
