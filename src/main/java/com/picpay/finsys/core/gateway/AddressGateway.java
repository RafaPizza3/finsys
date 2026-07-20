package com.picpay.finsys.core.gateway;

import com.picpay.finsys.core.domain.AddressDomain;

public interface AddressGateway {
    AddressDomain getAdressByZipCode(String zipCode);
}
