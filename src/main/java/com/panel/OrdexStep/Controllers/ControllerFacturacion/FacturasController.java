package com.panel.OrdexStep.Controllers.ControllerFacturacion;

import com.panel.OrdexStep.DTOS.NotaCreditoDto;
import com.panel.OrdexStep.DTOS.documentoSoporteBillRequest;
import com.panel.OrdexStep.Entity.FacturaResponse;
import com.panel.OrdexStep.Service.ServicioFacturacion.FacturaBillRequest;
import com.panel.OrdexStep.Service.ServicioFacturacion.FacturasService;
import com.panel.OrdexStep.Service.ServicioFacturacion.FactusRangosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturasController {

    @Autowired
    private FacturasService facturasService;

    @Autowired
    private FactusRangosService rangosService;

    @GetMapping("/rangos")
    public ResponseEntity<Object> getRangos() {
        return ResponseEntity.ok(rangosService.getRangos());
    }

    @PostMapping("/emitir")
    public ResponseEntity<FacturaResponse> emitirFactura(@RequestBody FacturaBillRequest request) {
        FacturaResponse response = facturasService.enviarFactura(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/actualizar/logo")
    public ResponseEntity<?> ActualizarLogoEmpresa(@RequestParam("image") MultipartFile imagenPostman) {
        System.out.println("¡MÉTODO ENTRANDO!");
        try {
            Object response = rangosService.actualizarLogoFactus(imagenPostman);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/emitir-nota/credito")
    public ResponseEntity<FacturaResponse>emitirNotaCredito(@RequestBody NotaCreditoDto dto){
        FacturaResponse res = facturasService.EmitirNotaCredito(dto);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/emitir/documento-soporte")
    public ResponseEntity<FacturaResponse>EmitirDocuemntoSoporte(documentoSoporteBillRequest request){
        try{
            FacturaResponse res = facturasService.emitirDocumentoSoporte(request);
            return ResponseEntity.ok(res);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}