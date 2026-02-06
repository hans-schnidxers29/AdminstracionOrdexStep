package com.panel.OrdexStep.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "planes") // Esta tabla se creará en tu DB
@Data // Si usas Lombok, si no, genera Getters y Setters
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre; // Ej: "Básico", "Pro", "Enterprise"

    @Column(nullable = false)
    private BigDecimal precio; // Costo mensual

    // --- LÍMITES DEL PLAN ---

    @Column(name = "max_usuarios")
    private Integer maxUsuarios;

    @Column(name = "max_empresas_secundarias") // Por si tu ERP es multi-sucursal
    private Integer maxSedes;

    @Column(name = "limite_facturas_mes")
    private Integer limiteFacturasMes;

    @Column(name = "acceso_reportes_avanzados")
    private Boolean accesoReportesAvanzados = false;

    @Column(name = "soporte_prioritario")
    private Boolean soportePrioritario = false;

    // --- RELACIÓN CON EMPRESAS ---

    @OneToMany(mappedBy = "plan")
    @JsonIgnore@JsonIgnoreProperties("plan")
    private List<Empresa> empresas;

    // Auditoría básica
    @Column(name = "activo")
    private Boolean activo = true;
}