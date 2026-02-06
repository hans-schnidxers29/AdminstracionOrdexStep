package com.panel.OrdexStep.DTOS;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SuscripcionDTO {
    private Long empresaId;
    private Long planId;
    private Boolean activo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estadoSuscripcion;
    private String tipoSuscripcion;
    private BigDecimal montoPagado;

}
