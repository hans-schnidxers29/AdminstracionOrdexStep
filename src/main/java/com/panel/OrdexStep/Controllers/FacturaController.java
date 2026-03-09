package com.panel.OrdexStep.Controllers;

import com.panel.OrdexStep.DTOS.FacturaERPPayload;
import com.panel.OrdexStep.Entity.FacturaResponse;
import com.panel.OrdexStep.Service.FacturacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/facturas")
public class FacturaController {
    
    private final FacturacionService facturacionService;

    public FacturaController(FacturacionService facturacionService) {
        this.facturacionService = facturacionService;
    }

    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPedido(@RequestBody FacturaERPPayload payload) {
        // El ERP nos manda la info, nosotros la procesamos con Factus
        FacturaResponse respuestaFactus = facturacionService.procesarYEnviarAFactus(payload);

        if(respuestaFactus.isStatus()) {
            return ResponseEntity.ok(respuestaFactus);
        } else {
            return ResponseEntity.badRequest().body(respuestaFactus);
        }
    }
}
