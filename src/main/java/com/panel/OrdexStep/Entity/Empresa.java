package com.panel.OrdexStep.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "empresa", schema = "public") // Apuntando al esquema del ERP
@Data
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "plan_id") // Esta es la columna nueva que controlarás desde el Admin
    private Plan plan;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Suscripcion> suscripciones;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "nit", unique = true)
    private String nit;

    @Email(message = "debe ser un correo legitimo")
    private String correoEmpresa;


}