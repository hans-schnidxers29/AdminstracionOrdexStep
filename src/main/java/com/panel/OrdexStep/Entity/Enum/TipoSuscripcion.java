package com.panel.OrdexStep.Entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public enum TipoSuscripcion {
    ANUAL(12),
    MENSUAL(1),
    TRIMESTRAL(3),
    PRUEBAS(0);

    private final int meses;

    public LocalDate calcularFechaVencimiento(LocalDate inicio) {
        return inicio.plusMonths(this.meses);
    }
}

