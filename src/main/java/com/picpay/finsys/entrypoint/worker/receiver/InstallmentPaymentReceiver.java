package com.picpay.finsys.entrypoint.worker.receiver;

import com.picpay.finsys.core.usecase.InstallmentPaymentUseCase;
import com.picpay.finsys.entrypoint.dto.request.InstallmentPaymentRequest;
import com.picpay.finsys.entrypoint.mapper.InstallmentMapperDTO;
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
public class InstallmentPaymentReceiver {
    private final InstallmentPaymentUseCase installmentPaymentUseCase;

    @Bean
    public Consumer<Message<InstallmentPaymentRequest>> installmentPaymentEvent() {
        return this::receive;
    }

    @SneakyThrows
    public void receive(Message<InstallmentPaymentRequest> message) {
        var payment = message.getPayload();
        installmentPaymentUseCase.execute(payment.getContractId(), payment.getInstallmentId(), payment.getPaymentAmount());
    }
}
