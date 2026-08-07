package com.picpay.finsys.entrypoint.dto.response;

import com.picpay.finsys.core.domain.enumeration.HistoryType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class HistoryResponse {
    private HistoryType type;
    private LocalDateTime date;
    private Double amount;
}
