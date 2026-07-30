package com.picpay.finsys.core.gateway;

import com.picpay.finsys.core.domain.AddressDomain;
import com.picpay.finsys.core.exception.InvalidZipCodeException;

public interface AddressGateway {
    AddressDomain getAddressByZipCode(String zipCode) throws InvalidZipCodeException;
}
