package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.usecase.*;
import com.picpay.finsys.entrypoint.dto.request.CustomerRequest;
import com.picpay.finsys.entrypoint.dto.request.CustomerUpdateRequest;
import com.picpay.finsys.entrypoint.dto.response.CustomerResponse;
import com.picpay.finsys.entrypoint.mapper.CustomerMapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public CustomerResponse insert(@RequestBody CustomerRequest customer) {
        CustomerDomain requestDomain = customerMapper.toDomain(customer);
        CustomerDomain responseDomain = insertCustomerUseCase.execute(requestDomain);
        return customerMapper.toResponse(responseDomain);
    }

    @PutMapping
    public CustomerDomain update(@RequestBody CustomerUpdateRequest data) throws CustomerNotFoundException {
        CustomerDomain domain = customerMapper.toDomain(data.getRequest());
        return updateCustomerUseCase.execute(data.getId(), domain);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) throws CustomerNotFoundException {
        deleteCustomerUseCase.execute(id);
    }
}
