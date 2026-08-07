package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.exception.CanceledInstallmentException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.ExceededInstallmentAmountInPaymentException;
import com.picpay.finsys.core.exception.InstallmentNotFoundException;
import com.picpay.finsys.core.exception.InstallmentPaymentPriorityException;
import com.picpay.finsys.core.exception.PaidInstallmentException;
import com.picpay.finsys.core.usecase.FindContractInstallmentsUseCase;
import com.picpay.finsys.core.usecase.FindInstallmentByIdUseCase;
import com.picpay.finsys.core.usecase.InstallmentPaymentUseCase;
import com.picpay.finsys.entrypoint.controller.api.InstallmentControllerAPI;
import com.picpay.finsys.entrypoint.dto.request.InstallmentPaymentRequest;
import com.picpay.finsys.entrypoint.dto.response.InstallmentResponse;
import com.picpay.finsys.entrypoint.mapper.InstallmentMapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/installments")
@RequiredArgsConstructor
public class InstallmentController implements InstallmentControllerAPI {
    private final FindContractInstallmentsUseCase findContractInstallmentsUseCase;
    private final FindInstallmentByIdUseCase findInstallmentByIdUseCase;
    private final InstallmentPaymentUseCase installmentPaymentUseCase;

    private final InstallmentMapperDTO installmentMapper;

    @Override
    @GetMapping("/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<InstallmentResponse> findInstallments(@PathVariable String contractId, @PageableDefault(6) Pageable page) throws ContractNotFoundException {
        Page<InstallmentDomain> domainPage = findContractInstallmentsUseCase.execute(contractId);
        List<InstallmentResponse> responseList = domainPage
                .stream()
                .map(installmentMapper::toResponse)
                .toList();

        return new PageImpl<>(responseList, page, domainPage.getTotalElements());
    }

    @Override
    @GetMapping("/{contractId}/{installmentId}")
    @ResponseStatus(HttpStatus.OK)
    public InstallmentResponse findInstallmentById(@PathVariable String contractId, @PathVariable String installmentId) throws InstallmentNotFoundException, ContractNotFoundException {
        InstallmentDomain installmentDomain = findInstallmentByIdUseCase.execute(contractId, installmentId);
        return installmentMapper.toResponse(installmentDomain);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstallmentResponse payInstallment(@RequestBody InstallmentPaymentRequest request) throws InstallmentNotFoundException, ExceededInstallmentAmountInPaymentException, ContractNotFoundException, InstallmentPaymentPriorityException, PaidInstallmentException, CanceledInstallmentException {
        InstallmentDomain installment = installmentPaymentUseCase.execute(request.getContractId(),
                request.getInstallmentId(),
                request.getPaymentAmount(), null);

        return installmentMapper.toResponse(installment);
    }
}
