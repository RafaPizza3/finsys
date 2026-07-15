package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.usecase.FindCustomerByStatusUseCase;
import com.picpay.finsys.core.usecase.FindAllCustomerUseCase;
import com.picpay.finsys.core.usecase.FindCustomerByIdUseCase;
import com.picpay.finsys.core.usecase.InsertCustomerUseCase;
import com.picpay.finsys.core.usecase.UpdateCustomerUseCase;
import com.picpay.finsys.core.usecase.DeleteCustomerUseCase;
import com.picpay.finsys.entrypoint.dto.request.CustomerRequest;
import com.picpay.finsys.entrypoint.dto.response.CustomerResponse;
import com.picpay.finsys.entrypoint.mapper.CustomerMapperDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerMapperDTO customerMapper;

    private final FindCustomerByStatusUseCase findCustomerByStatusUseCase;
    private final FindAllCustomerUseCase findAllCustomerUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final InsertCustomerUseCase insertCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    @GetMapping("/status/{status}")
    public List<CustomerResponse> findAllByStatus(@PathVariable CustomerStatus status) {
        List<CustomerDomain> domain = findCustomerByStatusUseCase.execute(status);
        return domain.stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        List<CustomerDomain> domain = findAllCustomerUseCase.execute();
        return domain.stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable String id) throws CustomerNotFoundException {
        CustomerDomain domain = findCustomerByIdUseCase.execute(id);
        return customerMapper.toResponse(domain);
    }

    @PostMapping
    public CustomerResponse insert(@RequestBody @Valid CustomerRequest customer) {
        CustomerDomain requestDomain = customerMapper.toDomain(customer);
        CustomerDomain responseDomain = insertCustomerUseCase.execute(requestDomain);
        return customerMapper.toResponse(responseDomain);
    }

    @PutMapping("/{id}")
    public CustomerDomain update(@PathVariable String id, @RequestBody @Valid CustomerRequest request) throws CustomerNotFoundException {
        CustomerDomain domain = customerMapper.toDomain(request);
        return updateCustomerUseCase.execute(id, domain);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) throws CustomerNotFoundException {
        deleteCustomerUseCase.execute(id);
    }
}
