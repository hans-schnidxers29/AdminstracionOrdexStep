package com.panel.OrdexStep.Entity.Enum;

import lombok.Getter;

@Getter
public enum EstadoPagoSuscripcion {

    PAGADO("Pagado"),
    PENDIENTE("Pendiente"),
    VENCIDO("Vencido");

    private final String descripcio;
    EstadoPagoSuscripcion(String descripcio){
        this.descripcio=descripcio;
    }

}
