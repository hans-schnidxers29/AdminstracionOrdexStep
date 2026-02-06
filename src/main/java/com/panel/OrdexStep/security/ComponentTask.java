package com.panel.OrdexStep.security;

import com.panel.OrdexStep.Service.SuscripcionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class ComponentTask {

    private final SuscripcionService suscripcionService;

    public ComponentTask(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void SuscripcionVerificar() {

        LocalDate fechaVencimiento = LocalDate.now();

        suscripcionService.cancelarSuscripcion(fechaVencimiento);

        suscripcionService.NotificacionSuscripcion(fechaVencimiento);

    }
}
