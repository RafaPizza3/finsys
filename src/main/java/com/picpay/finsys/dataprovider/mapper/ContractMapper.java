package com.picpay.finsys.dataprovider.mapper;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.dataprovider.entity.ContractEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    ContractDomain toDomain(ContractEntity entity);
    ContractEntity toEntity(ContractDomain domain);
}
