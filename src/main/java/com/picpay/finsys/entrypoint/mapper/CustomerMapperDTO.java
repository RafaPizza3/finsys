package com.picpay.finsys.entrypoint.mapper;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.entrypoint.dto.request.CustomerRequest;
import com.picpay.finsys.entrypoint.dto.request.CustomerUpdateRequest;
import com.picpay.finsys.entrypoint.dto.response.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapperDTO {
    CustomerResponse toResponse(CustomerDomain domain);
    CustomerDomain toDomain(CustomerRequest request);
    CustomerDomain toDomain(CustomerUpdateRequest request);
}
