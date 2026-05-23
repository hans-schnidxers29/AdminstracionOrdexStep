package com.panel.OrdexStep.Controllers.ControllerFacturacion;

import com.panel.OrdexStep.DTOS.NotaCreditoDto;
import com.panel.OrdexStep.DTOS.documentoSoporteBillRequest;
import com.panel.OrdexStep.Entity.FacturaResponse;
import com.panel.OrdexStep.Service.ServicioFacturacion.FacturaBillRequest;
import com.panel.OrdexStep.Service.ServicioFacturacion.FacturasService;
import com.panel.OrdexStep.Service.ServicioFacturacion.FactusRangosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturasController {

    private static final Logger log = LoggerFactory.getLogger(FacturasController.class);

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

        } catch (HttpClientErrorException e) {
            String errores = e.getResponseBodyAsString();

            log.error("error en crear y validar factura: {}" , errores);

             return ResponseEntity
                     .status(e.getStatusCode())
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(errores);

        }catch (Exception e) {

            log.error("error de programa interno ", e);

            Map<String, String> ResponseError = new HashMap<>();
            ResponseError.put("status", "Error Interno ");
            ResponseError.put("message", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseError);
        }
    }

    @PostMapping("/emitir-nota/credito")
    public ResponseEntity<?>emitirNotaCredito(@RequestBody NotaCreditoDto dto){

        try{
            FacturaResponse res = facturasService.EmitirNotaCredito(dto);
            return ResponseEntity.ok(res);

        } catch (HttpClientErrorException e) {
            // Captura errores específicos de la API de Factus (400, 422, 404)
            // e.getResponseBodyAsString() contiene el JSON exacto con el error que envió Factus
            String errorFactus = e.getResponseBodyAsString();

            log.error("Error de validación enviado por Factus: {}", errorFactus);

        return ResponseEntity
                .status(e.getStatusCode()) // Mantiene el mismo código de estado (ej: 422 Unprocessable Entity)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorFactus); // Le envía al cliente el JSON crudo del error para que sepa qué falló

        }catch (Exception e) {
        // Captura cualquier otro error inesperado (caída de servidor, error de código, etc.)
        log.error("Error inesperado al procesar documento soporte: ", e);

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "Error Interno");
        errorResponse.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
        }
    }


    @PostMapping("/emitir/documento-soporte")
    @ResponseBody
    public ResponseEntity<?> EmitirDocuemntoSoporte(@RequestBody documentoSoporteBillRequest request) {
        try {
            // Ejecuta el servicio que se comunica con Factus
            FacturaResponse res = facturasService.emitirDocumentoSoporte(request);
            log.info("emitiendo documento soporte: ");
            return ResponseEntity.ok(res);

        } catch (HttpClientErrorException e) {
            // Captura errores específicos de la API de Factus (400, 422, 404)
            // e.getResponseBodyAsString() contiene el JSON exacto con el error que envió Factus
            String FactusStatus = e.getResponseBodyAsString();

            log.error("Error de validación enviado  Factus: {}", FactusStatus);

            return ResponseEntity
                    .status(e.getStatusCode()) // Mantiene el mismo código de estado (ej: 422 Unprocessable Entity)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(FactusStatus); // Le envía al cliente el JSON crudo del error para que sepa qué falló

        } catch (Exception e) {
            // Captura cualquier otro error inesperado (caída de servidor, error de código, etc.)
            log.error("Error inesperado al procesar documento: ", e);

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "Error Interno");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}