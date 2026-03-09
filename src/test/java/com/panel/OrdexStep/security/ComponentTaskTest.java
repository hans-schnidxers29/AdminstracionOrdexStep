package com.panel.OrdexStep.security;

import com.panel.OrdexStep.Service.SuscripcionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ComponentTaskTest {

    @MockitoBean
    private SuscripcionService suscripcionService;

    @Autowired
    private ComponentTask componentTask;

    @Test
    void testSuscripcionVerificarLlamadaServicios() {
        // Ejecutamos el método manualmente para el test
        componentTask.SuscripcionVerificar();

        // Verificamos que se llamó al método de cancelación
        verify(suscripcionService, times(1)).cancelarSuscripcion(any(LocalDate.class));
        verify(suscripcionService, times(1)).NotificacionSuscripcion(any(LocalDate.class));

        System.out.println("Test completado con éxito.");
    }
}