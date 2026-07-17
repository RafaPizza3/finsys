package com.picpay.finsys.entrypoint.mapper;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.entrypoint.dto.request.ContractRequest;
import com.picpay.finsys.entrypoint.dto.request.ContractUpdateRequest;
import com.picpay.finsys.entrypoint.dto.response.ContractResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContractMapperDTO {
    ContractResponse toResponse(ContractDomain domain);
    ContractDomain toDomain(ContractRequest request);
    ContractDomain toDomain(ContractUpdateRequest request);
}
