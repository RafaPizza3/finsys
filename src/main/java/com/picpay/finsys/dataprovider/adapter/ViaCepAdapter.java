package com.picpay.finsys.dataprovider.adapter;

import com.picpay.finsys.core.domain.AddressDomain;
import com.picpay.finsys.core.exception.InvalidZipCodeException;
import com.picpay.finsys.core.gateway.AddressGateway;
import com.picpay.finsys.dataprovider.client.ViaCepClient;
import com.picpay.finsys.dataprovider.client.response.ViaCepResponse;
import com.picpay.finsys.dataprovider.mapper.AddressMapper;
import feign.FeignException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViaCepAdapter implements AddressGateway {

    private final ViaCepClient client;
    private final AddressMapper mapper;

    @Override
    @Retry(name = "viaCepRetry", fallbackMethod = "getAddressByZipCodeFallback")
    public AddressDomain getAddressByZipCode(String zipCode) throws InvalidZipCodeException {
        try {
            ViaCepResponse response = client.getClient(zipCode);
            return mapper.toDomain(response);
        } catch (FeignException fe) {
            throw new InvalidZipCodeException(zipCode);
        }
    }

    public ViaCepResponse getAddressByZipCodeFallback(String zipCode, Throwable throwable) throws InvalidZipCodeException {
        log.error("Failed to get address by zip code: " + zipCode + " reason: " + throwable.getMessage());
        throw new InvalidZipCodeException(zipCode);
    }
}
