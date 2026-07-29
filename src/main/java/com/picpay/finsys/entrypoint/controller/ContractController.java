package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.ActiveContractException;
import com.picpay.finsys.core.exception.CanceledContractException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.ContractWithPaidInstallmentException;
import com.picpay.finsys.core.exception.FinishedContractException;
import com.picpay.finsys.core.usecase.CancelContractUseCase;
import com.picpay.finsys.core.usecase.FindContractByStatusUseCase;
import com.picpay.finsys.core.usecase.FindAllContractUseCase;
import com.picpay.finsys.core.usecase.FindContractByIdUseCase;
import com.picpay.finsys.core.usecase.InsertContractUseCase;
import com.picpay.finsys.core.usecase.UpdateContractUseCase;
import com.picpay.finsys.core.usecase.DeleteContractUseCase;
import com.picpay.finsys.entrypoint.controller.api.ContractControllerAPI;
import com.picpay.finsys.entrypoint.dto.request.ContractRequest;
import com.picpay.finsys.entrypoint.dto.request.ContractUpdateRequest;
import com.picpay.finsys.entrypoint.dto.response.ContractResponse;
import com.picpay.finsys.entrypoint.mapper.ContractMapperDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/v1/contracts")
@RequiredArgsConstructor
public class ContractController implements ContractControllerAPI {
    private final ContractMapperDTO contractMapper;

    private final FindContractByStatusUseCase findContractByStatusUseCase;
    private final FindAllContractUseCase findAllContractUseCase;
    private final FindContractByIdUseCase findContractByIdUseCase;
    private final InsertContractUseCase insertContractUseCase;
    private final UpdateContractUseCase updateContractUseCase;
    private final CancelContractUseCase cancelContractUseCase;
    private final DeleteContractUseCase deleteContractUseCase;

    @Override
    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ContractResponse> findAllByStatus(
            @PathVariable ContractStatus status, @PageableDefault(size = 5) Pageable page
    ) {
        Page<ContractDomain> domainPage = findContractByStatusUseCase.execute(status, page);
        List<ContractResponse> domainList = domainPage
                .stream()
                .map(contractMapper::toResponse)
                .toList();

        return new PageImpl<>(domainList, page, domainPage.getTotalElements());
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ContractResponse> findAll(@PageableDefault(size = 5) Pageable page) {
        Page<ContractDomain> domainPage = findAllContractUseCase.execute(page);
        List<ContractResponse> domainList = domainPage
                .stream()
                .map(contractMapper::toResponse)
                .toList();

        return new PageImpl<>(domainList, page, domainPage.getTotalElements());
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractResponse findById(@PathVariable String id) throws ContractNotFoundException {
        ContractDomain domain = findContractByIdUseCase.execute(id);
        return contractMapper.toResponse(domain);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse insert(@RequestBody @Valid ContractRequest contract) throws BadRequestException {
        ContractDomain requestDomain = contractMapper.toDomain(contract);
        ContractDomain responseDomain = insertContractUseCase.execute(requestDomain);
        return contractMapper.toResponse(responseDomain);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse update(@PathVariable String id, @RequestBody ContractUpdateRequest request) throws BadRequestException {
        ContractDomain requestDomain = contractMapper.toDomain(request);
        requestDomain.setId(id);
        ContractDomain responseDomain = updateContractUseCase.execute(id, requestDomain);
        return contractMapper.toResponse(responseDomain);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable String id) throws CanceledContractException, FinishedContractException, ContractWithPaidInstallmentException, ContractNotFoundException {
        cancelContractUseCase.execute(id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) throws ActiveContractException, ContractNotFoundException {
        deleteContractUseCase.execute(id);
    }
}
