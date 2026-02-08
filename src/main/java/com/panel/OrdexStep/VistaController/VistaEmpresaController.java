package com.panel.OrdexStep.VistaController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empresas")
public class VistaEmpresaController {


    @GetMapping("/Lista")
    public String MostrarFromListaEmpresas(){
        return "ListadoEmpresa";
    }
}
