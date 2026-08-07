package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.CustomerDomain;

public interface CreateSessionUseCase {
    CustomerDomain execute(String email, String password);
}
