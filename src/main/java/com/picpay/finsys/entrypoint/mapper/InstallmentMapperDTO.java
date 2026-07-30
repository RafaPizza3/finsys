package com.picpay.finsys.entrypoint.mapper;

import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.entrypoint.dto.response.InstallmentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstallmentMapperDTO {
    InstallmentResponse toResponse(InstallmentDomain domain);
}
