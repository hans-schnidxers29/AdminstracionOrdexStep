package com.panel.OrdexStep.Repository;

import com.panel.OrdexStep.Entity.UsuariosAdministradores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioAdmiRepositorio extends JpaRepository<UsuariosAdministradores,Long> {

    Optional<UsuariosAdministradores> findByEmail(String email);
    Optional<UsuariosAdministradores> findByUsuario(String usuario);
}
