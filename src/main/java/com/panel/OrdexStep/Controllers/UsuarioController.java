package com.panel.OrdexStep.Controllers;

import com.panel.OrdexStep.Entity.UsuariosAdministradores;
import com.panel.OrdexStep.Service.UsuarioAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioAdminService usuarioAdminService;

    @PostMapping("/crear")
    public ResponseEntity<?>crearUsuario(@RequestBody UsuariosAdministradores user){
        try{
            usuarioAdminService.CrearUsuarioAdmin(user);
            return ResponseEntity.ok().body("creado Exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("listar")
    public ResponseEntity<?>listaUserAdmin(){
        try {
            return ResponseEntity.ok(usuarioAdminService.listaUsuarios());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
