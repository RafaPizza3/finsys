package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/contract")
@RequiredArgsConstructor
public class ContractController {
    private final FindContractByStatusUseCase findContractByStatusUseCase;
    private final FindAllContractUseCase findAllContractUseCase;
    private final FindContractByIdUseCase findContractByIdUseCase;
    private final InsertContractUseCase insertContractUseCase;
    private final UpdateContractUseCase updateContractUseCase;
    private final DeleteContractUseCase deleteContractUseCase;

    @GetMapping("/status/{status}")
    public List<ContractDomain> findAllByStatus(@PathVariable ContractStatus status) {
        return findContractByStatusUseCase.execute(status);
    }

    @GetMapping
    public List<ContractDomain> findAll() {
        return findAllContractUseCase.execute();
    }

    @GetMapping("/{id}")
    public ContractDomain findById(String id) {
        return findContractByIdUseCase.execute(id);
    }

    @PostMapping
    public ContractDomain insert(@RequestBody ContractDomain contract) {
        return insertContractUseCase.execute(contract);
    }

    @PutMapping
    public ContractDomain update(@RequestBody ContractDomain contract) {
        return updateContractUseCase.execute(contract);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        deleteContractUseCase.execute(id);
    }
}
