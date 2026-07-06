package com.picpay.finsys.core.domain.enumeration;

public enum InstallmentStatus {
    OPEN("open"),
    OVERDUE("overdue"),
    PAID("paid"),
    CANCELED("canceled");

    public String value;
    InstallmentStatus(String value) {
        this.value = value;
    }
}
