package com.picpay.finsys.entrypoint.mapper;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.entrypoint.dto.request.CustomerRequest;
import com.picpay.finsys.entrypoint.dto.response.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapperDTO {
    CustomerDomain toDomain(CustomerRequest request);
    CustomerResponse toResponse(CustomerDomain domain);
}
