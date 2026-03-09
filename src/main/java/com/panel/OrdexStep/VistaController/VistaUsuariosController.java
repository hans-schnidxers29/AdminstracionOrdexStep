package com.panel.OrdexStep.VistaController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaUsuariosController {

    @GetMapping("/login")
    public String login() {
        return "Login/login";
    }

    @GetMapping("/usuarios/crear")
    public String FormCrearUsuarios(){
        return "Usuarios/CrearUsuario";
    }



}
