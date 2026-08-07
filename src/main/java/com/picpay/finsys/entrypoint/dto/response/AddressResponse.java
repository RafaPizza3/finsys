package com.picpay.finsys.entrypoint.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressResponse {
    private String zipCode;
    private String address;
    private String neighborhood;
    private String city;
    private String federativeUnity;
    private String number;
    private String detail;
}
