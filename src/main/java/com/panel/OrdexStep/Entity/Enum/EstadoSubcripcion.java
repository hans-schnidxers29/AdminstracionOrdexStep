package com.panel.OrdexStep.Entity.Enum;

import lombok.Getter;

@Getter
public enum EstadoSubcripcion {

    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    SUSPENDIDO("Suspendido");

    private final String descripcion;

    EstadoSubcripcion(String descripcion){
        this.descripcion=descripcion;
    }
}
