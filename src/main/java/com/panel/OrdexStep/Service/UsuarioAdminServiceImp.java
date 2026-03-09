package com.panel.OrdexStep.Service;

import com.panel.OrdexStep.Entity.Roles;
import com.panel.OrdexStep.Entity.UsuariosAdministradores;
import com.panel.OrdexStep.Repository.UsuarioAdmiRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class UsuarioAdminServiceImp implements UsuarioAdminService{

    @Autowired
    private UsuarioAdmiRepositorio repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UsuariosAdministradores> listaUsuarios() {
        return repo.findAll();
    }

    @Override
    public void CrearUsuarioAdmin(UsuariosAdministradores user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Asignar rol por defecto si no tiene ninguno
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            // Aquí se asume que existe al menos un rol. En una app real, buscaríamos en la DB
            // o crearíamos el rol si no existe. Para este paso, simplemente evitaremos nulls.
            // Si el sistema ya maneja roles, esta parte se ajustará según la lógica de negocio.
            user.setRoles(Collections.emptyList());
        }
        
        repo.save(user);
    }
}
