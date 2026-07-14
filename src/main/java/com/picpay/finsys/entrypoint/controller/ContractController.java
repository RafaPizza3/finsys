package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.usecase.*;
import com.picpay.finsys.entrypoint.dto.request.ContractRequest;
import com.picpay.finsys.entrypoint.dto.request.ContractUpdateRequest;
import com.picpay.finsys.entrypoint.dto.response.ContractResponse;
import com.picpay.finsys.entrypoint.mapper.ContractMapperDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<ContractResponse> findAllByStatus(@PathVariable ContractStatus status) {
        List<ContractDomain> domainList = findContractByStatusUseCase.execute(status);
        return domainList.stream()
                .map(contractMapper::toResponse)
                .toList();
    }

    @GetMapping
    public List<ContractResponse> findAll() {
        List<ContractDomain> domainList = findAllContractUseCase.execute();
        return domainList.stream()
                .map(contractMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ContractResponse findById(@PathVariable String id) throws ContractNotFoundException {
        ContractDomain domain = findContractByIdUseCase.execute(id);
        return contractMapper.toResponse(domain);
    }

    @PostMapping
    public ContractResponse insert(@RequestBody @Valid ContractRequest contract) {
        ContractDomain requestDomain = contractMapper.toDomain(contract);
        ContractDomain responseDomain = insertContractUseCase.execute(requestDomain);
        return contractMapper.toResponse(responseDomain);
    }

    @PutMapping
    public ContractResponse update(@RequestBody @Valid ContractUpdateRequest data) throws ContractNotFoundException {
        ContractDomain requestDomain = contractMapper.toDomain(data.getRequest());
        ContractDomain responseDomain = updateContractUseCase.execute(data.getId(), requestDomain);
        return contractMapper.toResponse(responseDomain);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) throws ContractNotFoundException {
        deleteContractUseCase.execute(id);
    }
}
