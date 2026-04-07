package com.panel.OrdexStep.Service.ServicioFacturacion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.panel.OrdexStep.DTOS.NotaCreditoDto;
import com.panel.OrdexStep.DTOS.documentoSoporteBillRequest;
import com.panel.OrdexStep.Entity.FacturaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacturasService {

    private final String URL_FACTURA = "https://api-sandbox.factus.com.co/v1/bills/validate";

    private final String URL_NOTA_CREDITO = "https://api-sandbox.factus.com.co/v1/credit-notes/validate";

    private final String URL_DOCUMENTO_SOPORTE = "https://api-sandbox.factus.com.co/v1/documents/validate";

    @Autowired
    private FactusAuthService authService;

    private final RestTemplate restTemplate = new RestTemplate();

    public FacturaResponse enviarFactura(FacturaBillRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + authService.getAccessToken());
        headers.set("Accept", "application/json");

        HttpEntity<FacturaBillRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    URL_FACTURA, entity, String.class  // ← String, no FacturaResponse
            );

            if (response.getBody() == null) {
                System.err.println("¡ERROR! Factus respondió con cuerpo vacío. Status Code: " + response.getStatusCode());
                throw new RuntimeException("Factus devolvió body null. Status: " + response.getStatusCode());
            }

            String json = response.getBody();
            System.out.println("RESPUESTA FACTUS RAW: " + json);  // ← ver JSON real

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(json, FacturaResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar factura a Factus: " + e.getMessage());
        }
    }

    // EN EL PANEL
    public FacturaResponse EmitirNotaCredito(NotaCreditoDto dto) {
        Map<String, Object> requestBody = new HashMap<>();

        System.out.println( "entrando al panel " + dto.getBillId());
        requestBody.put("bill_id", dto.getBillId());
        requestBody.put("correction_concept_code", dto.getCorrectionConcept()); // Aquí entra el 1 que enviaste
        requestBody.put("customization_id", 20);
        requestBody.put("reference_code", dto.getReferenceCode());
        requestBody.put("payment_method_code", dto.getMetodoPago());
        requestBody.put("observation", dto.getObservation());
        requestBody.put("items", dto.getItems());
        requestBody.put("numbering_range_id", dto.getRange_numeracion());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // USAR try-catch para capturar el error de FACTUS y devolverlo al ERP
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(URL_NOTA_CREDITO, entity, String.class);
            String json = response.getBody();

            System.out.println("RESPUESTA FACTUS RAW: " + json);
            return new ObjectMapper().readValue(response.getBody(), FacturaResponse.class);
        } catch (HttpStatusCodeException e) {
            // Si Factus falla (400, 500), devolvemos el error real para que el ERP lo vea
            System.err.println("Error de Factus: " + e.getResponseBodyAsString());
            throw new RuntimeException("Factus respondió error: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado en Panel: " + e.getMessage());
        }
    }

    public FacturaResponse emitirDocumentoSoporte(documentoSoporteBillRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + authService.getAccessToken());
        headers.set("Accept", "application/json");

        HttpEntity<documentoSoporteBillRequest> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(URL_DOCUMENTO_SOPORTE, entity, String.class);
            if (response.getBody() == null) {
                System.out.println("error al crear docuemento: " + response.getStatusCode());
                throw new RuntimeException("error al crear docuemento: " + response.getStatusCode());
            }
            String json = response.getBody();
            System.out.println("RESPUESTA FACTUS RAW: " + json);  // ← ver JSON real

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            return mapper.readValue(json, FacturaResponse.class);

        }catch (HttpClientErrorException e) {
                // Aquí capturas errores 400, 401, 422 (Errores de validación de Factus/DIAN)
                String errorBody = e.getResponseBodyAsString();
                System.err.println("Error de Factus (Validación): " + errorBody);
                throw new RuntimeException("Error en Documento Soporte: " + errorBody);

        } catch (Exception e){
            throw new RuntimeException(e.getMessage());

        }
    }
}