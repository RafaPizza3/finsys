package com.picpay.finsys.entrypoint.controller;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.CustomerStatus;
import com.picpay.finsys.core.exception.ActiveCustomerException;
import com.picpay.finsys.core.exception.CustomerHasContractException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.exception.CustomerTooYoungException;
import com.picpay.finsys.core.exception.InvalidZipCodeException;
import com.picpay.finsys.core.usecase.FindCustomerByStatusUseCase;
import com.picpay.finsys.core.usecase.FindAllCustomerUseCase;
import com.picpay.finsys.core.usecase.FindCustomerByIdUseCase;
import com.picpay.finsys.core.usecase.InactivateCustomerUseCase;
import com.picpay.finsys.core.usecase.InsertCustomerUseCase;
import com.picpay.finsys.core.usecase.UpdateCustomerUseCase;
import com.picpay.finsys.core.usecase.DeleteCustomerUseCase;
import com.picpay.finsys.entrypoint.controller.api.CustomerControllerAPI;
import com.picpay.finsys.entrypoint.dto.request.CustomerRequest;
import com.picpay.finsys.entrypoint.dto.request.CustomerUpdateRequest;
import com.picpay.finsys.entrypoint.dto.response.CustomerResponse;
import com.picpay.finsys.entrypoint.mapper.CustomerMapperDTO;
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
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController implements CustomerControllerAPI {
    private final CustomerMapperDTO customerMapper;

    private final FindCustomerByStatusUseCase findCustomerByStatusUseCase;
    private final FindAllCustomerUseCase findAllCustomerUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final InsertCustomerUseCase insertCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final InactivateCustomerUseCase inactivateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    @Override
    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CustomerResponse> findAllByStatus(
            @PathVariable CustomerStatus status,
            @PageableDefault(size = 5) Pageable page
    ) {
        Page<CustomerDomain> domainPage = findCustomerByStatusUseCase.execute(status, page);
        List<CustomerResponse> domainList = domainPage
                .stream()
                .map(customerMapper::toResponse)
                .toList();

        return new PageImpl<>(domainList, page, domainPage.getTotalElements());
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CustomerResponse> findAll(@PageableDefault(size = 5) Pageable page) {
        Page<CustomerDomain> domainPage = findAllCustomerUseCase.execute(page);
        List<CustomerResponse> domainList = domainPage
                .stream()
                .map(customerMapper::toResponse)
                .toList();

        return new PageImpl<>(domainList, page, domainPage.getTotalElements());
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findById(@PathVariable String id) throws CustomerNotFoundException {
        CustomerDomain domain = findCustomerByIdUseCase.execute(id);
        return customerMapper.toResponse(domain);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse insert(@RequestBody @Valid CustomerRequest customer) throws InvalidZipCodeException, CustomerTooYoungException {
        CustomerDomain requestDomain = customerMapper.toDomain(customer);
        CustomerDomain responseDomain = insertCustomerUseCase.execute(requestDomain, customer.getZipCode());
        return customerMapper.toResponse(responseDomain);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse update(@PathVariable String id, @RequestBody CustomerUpdateRequest request) throws BadRequestException {
        CustomerDomain domain = customerMapper.toDomain(request);
        domain.setId(id);
        CustomerDomain responseDomain = updateCustomerUseCase.execute(id, domain, request.getZipCode());
        return customerMapper.toResponse(responseDomain);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse inactivate(@PathVariable String id) throws CustomerHasContractException, CustomerNotFoundException {
        CustomerDomain customerDomain = inactivateCustomerUseCase.execute(id);
        return customerMapper.toResponse(customerDomain);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) throws CustomerHasContractException, CustomerNotFoundException, ActiveCustomerException {
        deleteCustomerUseCase.execute(id);
    }
}
