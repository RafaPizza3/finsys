package com.picpay.finsys.core.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDomain {
    private String zipCode;
    private String address;
    private String neighborhood;
    private String city;
    private String federativeUnity;
}
