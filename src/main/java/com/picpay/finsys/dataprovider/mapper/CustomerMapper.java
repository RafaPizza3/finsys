package com.picpay.finsys.dataprovider.mapper;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.dataprovider.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDomain toDomain(CustomerEntity entity);
    CustomerEntity toEntity(CustomerDomain domain);
}
