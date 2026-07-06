package com.picpay.finsys.core.domain.enumeration;

public enum ContractStatusEnum {
        ACTIVE("active"),
        FINISHED("finished"),
        CANCELED("canceled");

        public String value;
        ContractStatusEnum(String value) {
                this.value = value;
        }
}