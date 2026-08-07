package com.picpay.finsys.dataprovider.entity;

import com.picpay.finsys.core.domain.enumeration.HistoryType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class HistoryEntity {
    private HistoryType type;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    private Double amount;
}
