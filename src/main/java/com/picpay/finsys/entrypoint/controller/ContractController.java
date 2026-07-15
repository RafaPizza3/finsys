package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.usecase.FindContractByStatusUseCase;
import com.picpay.finsys.core.usecase.FindAllContractUseCase;
import com.picpay.finsys.core.usecase.FindContractByIdUseCase;
import com.picpay.finsys.core.usecase.InsertContractUseCase;
import com.picpay.finsys.core.usecase.UpdateContractUseCase;
import com.picpay.finsys.core.usecase.DeleteContractUseCase;
import com.picpay.finsys.entrypoint.dto.request.ContractRequest;
import com.picpay.finsys.entrypoint.dto.response.ContractResponse;
import com.picpay.finsys.entrypoint.mapper.ContractMapperDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/v1/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractMapperDTO contractMapper;

    private final FindContractByStatusUseCase findContractByStatusUseCase;
    private final FindAllContractUseCase findAllContractUseCase;
    private final FindContractByIdUseCase findContractByIdUseCase;
    private final InsertContractUseCase insertContractUseCase;
    private final UpdateContractUseCase updateContractUseCase;
    private final DeleteContractUseCase deleteContractUseCase;

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContractResponse> findAllByStatus(@PathVariable ContractStatus status) {
        List<ContractDomain> domainList = findContractByStatusUseCase.execute(status);
        return domainList.stream()
                .map(contractMapper::toResponse)
                .toList();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContractResponse> findAll() {
        List<ContractDomain> domainList = findAllContractUseCase.execute();
        return domainList.stream()
                .map(contractMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractResponse findById(@PathVariable String id) throws ContractNotFoundException {
        ContractDomain domain = findContractByIdUseCase.execute(id);
        return contractMapper.toResponse(domain);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse insert(@RequestBody @Valid ContractRequest contract) {
        ContractDomain requestDomain = contractMapper.toDomain(contract);
        ContractDomain responseDomain = insertContractUseCase.execute(requestDomain);
        return contractMapper.toResponse(responseDomain);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse update(@PathVariable String id, @RequestBody @Valid ContractRequest request) throws ContractNotFoundException {
        ContractDomain requestDomain = contractMapper.toDomain(request);
        ContractDomain responseDomain = updateContractUseCase.execute(id, requestDomain);
        return contractMapper.toResponse(responseDomain);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) throws ContractNotFoundException {
        deleteContractUseCase.execute(id);
    }
}
