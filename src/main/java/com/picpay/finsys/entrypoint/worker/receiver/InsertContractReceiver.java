package com.picpay.finsys.entrypoint.worker.receiver;

import com.picpay.finsys.core.usecase.InsertContractUseCase;
import com.picpay.finsys.entrypoint.dto.request.ContractRequest;
import com.picpay.finsys.entrypoint.mapper.ContractMapperDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class InsertContractReceiver {

    private final InsertContractUseCase insertContractUseCase;

    private final ContractMapperDTO contractMapper;

    @Bean
    public Consumer<Message<ContractRequest>> insertContractEvent() {
       return this::receive;
    }

    @SneakyThrows
    public void receive(Message<ContractRequest> message) {
        var contract = contractMapper.toDomain(message.getPayload());
        insertContractUseCase.execute(contract);
    }
}
