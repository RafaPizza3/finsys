package com.picpay.finsys.dataprovider.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressEntity {
    private String zipCode;
    private String address;
    private String neighborhood;
    private String city;
    private String federativeUnity;
}
