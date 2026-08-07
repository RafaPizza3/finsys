package com.picpay.finsys.core.domain;

import com.picpay.finsys.core.domain.enumeration.HistoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class HistoryDomain {
    private LocalDateTime date;
    private HistoryType type;
    private Double amount;
}
