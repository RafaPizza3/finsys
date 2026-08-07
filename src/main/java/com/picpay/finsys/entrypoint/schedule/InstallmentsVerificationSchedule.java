package com.picpay.finsys.entrypoint.schedule;

import com.picpay.finsys.core.usecase.InstallmentsPaymentVerification;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

@Component
@RequiredArgsConstructor
public class InstallmentsVerificationSchedule {
    private final InstallmentsPaymentVerification installmentsPaymentVerification;

    private final Logger log = LoggerFactory.getLogger(InstallmentsVerificationSchedule.class);

    @Scheduled(cron = "0 */10 * * * *")
    public void verifyInstallmentsPayment() {
        log.info("starting contracts scan");
        installmentsPaymentVerification.execute();
    }
}
