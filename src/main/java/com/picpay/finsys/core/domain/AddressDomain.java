package com.picpay.finsys.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDomain {
    private String zipCode;
    private String address;
    private String neighborhood;
    private String city;
    private String federativeUnity;
    private String number;
    private String detail;
}
