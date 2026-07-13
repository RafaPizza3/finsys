package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final FindCustomerByStatusUseCase findCustomerByStatusUseCase;
    private final FindAllCustomerUseCase findAllCustomerUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final InsertCustomerUseCase insertCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    @GetMapping("/status/{status}")
    public List<CustomerDomain> findAllByStatus(@PathVariable CustomerStatus status) {
        return findCustomerByStatusUseCase.execute(status);
    }

    @GetMapping
    public List<CustomerDomain> findAll() {
        return findAllCustomerUseCase.execute();
    }

    @GetMapping("/{id}")
    public CustomerDomain findById(@PathVariable String id) {
        return findCustomerByIdUseCase.execute(id);
    }

    @PostMapping
    public CustomerDomain insert(@RequestBody CustomerDomain customer) {
        return insertCustomerUseCase.execute(customer);
    }

    @PutMapping
    public CustomerDomain update(@RequestBody CustomerDomain customer) {
        return updateCustomerUseCase.execute(customer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        deleteCustomerUseCase.execute(id);
    }
}
