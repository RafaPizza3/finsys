package com.picpay.finsys.core.domain.enumeration;

public enum CustomerStatus {
    ACTIVE("active"),
    INACTIVE("inactive");

    public String value;
    CustomerStatus(String value) {
        this.value = value;
    }
}
