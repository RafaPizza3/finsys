package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.usecase.DeleteContractUseCase;
import com.picpay.finsys.core.usecase.InsertContractUseCase;
import com.picpay.finsys.core.usecase.ListAllContractUseCase;
import com.picpay.finsys.core.usecase.UpdateContractUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ListAllContractUseCase listAllContractUseCase;
    private final InsertContractUseCase insertContractUseCase;
    private final UpdateContractUseCase updateContractUseCase;
    private final DeleteContractUseCase deleteContractUseCase;

    @GetMapping
    public List<ContractDomain> listAll() {
        return listAllContractUseCase.execute();
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
