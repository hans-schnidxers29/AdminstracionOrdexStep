package com.panel.OrdexStep.Service;

import com.panel.OrdexStep.Entity.UsuariosAdministradores;

import java.util.List;

public interface UsuarioAdminService {
    List<UsuariosAdministradores> listaUsuarios();
    void CrearUsuarioAdmin(UsuariosAdministradores user);
}
