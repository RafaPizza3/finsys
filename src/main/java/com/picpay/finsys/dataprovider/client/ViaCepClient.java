package com.picpay.finsys.dataprovider.client;

import com.picpay.finsys.dataprovider.client.response.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ViaCepCLient", url = "${finsys.viacep.url}")
public interface ViaCepClient {
    @GetMapping("/{zipCode}/json")
    ViaCepResponse getClient(@PathVariable String zipCode);
}
