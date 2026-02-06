package com.panel.OrdexStep.Service;

import com.panel.OrdexStep.Entity.Empresa;
import com.panel.OrdexStep.Entity.Suscripcion;

import java.time.LocalDate;

public interface SuscripcionService {

    void NotificacionSuscripcion(LocalDate fechaVencimiento);
    void cancelarSuscripcion(LocalDate fechaVencimiento);
    Suscripcion ObtenerEmpresaAndSuscripciones(Long id);
    void saveSuscripcion(Suscripcion suscripcion);
    void verificarSuscripcion(Empresa empresa);

}
