package com.picpay.finsys.dataprovider.mapper;

import com.picpay.finsys.core.domain.AddressDomain;
import com.picpay.finsys.dataprovider.client.response.ViaCepResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "zipCode", source = "response.cep")
    @Mapping(target = "address", source = "response.logradouro")
    @Mapping(target = "neighborhood", source = "response.bairro")
    @Mapping(target = "city", source = "response.localidade")
    @Mapping(target = "federativeUnity", source = "response.uf")
    AddressDomain toDomain(ViaCepResponse response);
}
