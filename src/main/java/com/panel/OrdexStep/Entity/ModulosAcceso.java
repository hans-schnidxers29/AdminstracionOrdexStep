package com.panel.OrdexStep.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "modulos_acceso", schema = "public") // Apuntando al esquema del ERP
@Data
public class ModulosAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre; // Ej: "VENTAS", "INVENTARIO", "DOC_ELECTRONICOS"

}
