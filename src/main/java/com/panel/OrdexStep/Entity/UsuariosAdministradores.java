package com.panel.OrdexStep.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="usuarioAdmin")
@Data
public class UsuariosAdministradores {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="usuario",unique = true)
    private String usuario;
    @Column(name="email",unique = true,nullable = false)
    private String email ;
    @Column(name="password")
    private String password ;
    // Cambiado a ManyToMany para que varios usuarios puedan compartir el mismo Rol (ADMIN, USER)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(  
            name = "usuarios_roles_admin",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Roles> roles;



}
