package com.picpay.finsys.core.gateway;

import com.picpay.finsys.core.domain.CustomerDomain;

public interface AuthGateway {
    String create(CustomerDomain customer);
    CustomerDomain auth(String email, String password);
}
