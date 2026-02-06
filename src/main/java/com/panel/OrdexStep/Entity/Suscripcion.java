package com.panel.OrdexStep.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.panel.OrdexStep.Entity.Enum.EstadoPagoSuscripcion;
import com.panel.OrdexStep.Entity.Enum.EstadoSubcripcion;
import com.panel.OrdexStep.Entity.Enum.TipoSuscripcion;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "suscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la empresa (quién paga)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonBackReference
    private Empresa empresa;

    // Relación con el plan (qué nivel de servicio tiene)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "monto_pagado", precision = 10, scale = 2)
    private BigDecimal montoPagado;

    @Enumerated(EnumType.STRING)
    private EstadoPagoSuscripcion estadoPago; // Ej: "PAGADO", "PENDIENTE", "VENCIDO"

    @Column(name = "renovacion_automatica")
    private Boolean renovacionAutomatica = true;

    @Enumerated(EnumType.STRING)
    private EstadoSubcripcion  estadoSub;

    @Enumerated(EnumType.STRING)
    private TipoSuscripcion tipoSuscripcion;

    // Método de ayuda para saber si la suscripción sigue vigente
    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return (hoy.isEqual(fechaInicio) || hoy.isAfter(fechaInicio)) && hoy.isBefore(fechaFin);
    }
}