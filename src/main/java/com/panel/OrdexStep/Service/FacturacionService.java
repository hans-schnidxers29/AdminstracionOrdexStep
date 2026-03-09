package com.panel.OrdexStep.Service;

import com.panel.OrdexStep.DTOS.CustomerRequest;
import com.panel.OrdexStep.DTOS.FacturaERPPayload;
import com.panel.OrdexStep.DTOS.FacturaRequest;
import com.panel.OrdexStep.DTOS.ItemRequest;
import com.panel.OrdexStep.Entity.FacturaResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturacionService {
    
    private final FactusAuthService authService;
    private final RestTemplate restTemplate = new RestTemplate();

    public FacturacionService(FactusAuthService authService) {
        this.authService = authService;
    }

    // URL de Factus para crear facturas (v1 es el estándar actual)
    private final String URL_BILL = "https://api-sandbox.factus.com.co/v1/bills";

    public FacturaResponse procesarYEnviarAFactus(FacturaERPPayload payload) {
        try {
            // 1. Obtener Token Válido
            String token = authService.getAccessToken();

            // 2. Transformar datos del ERP al formato de FACTUS
            FacturaRequest request = mapearDatos(payload);

            // 3. Configurar Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<FacturaRequest> entity = new HttpEntity<>(request, headers);

            // 4. Enviar a Factus
            ResponseEntity<FacturaResponse> response = restTemplate.postForEntity(
                    URL_BILL,
                    entity,
                    FacturaResponse.class
            );

            return response.getBody();

        } catch (HttpClientErrorException e) {
            // Error 400 o 422: Validaciones de la DIAN fallidas
            System.out.println("Error de validación: " + e.getResponseBodyAsString());
            throw new RuntimeException("Error DIAN: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado en el Panel: " + e.getMessage());
        }
    }

    /**
     * Este método traduce tus objetos del ERP a lo que Factus pide
     */
    private FacturaRequest mapearDatos(FacturaERPPayload payload) {
        FacturaRequest req = new FacturaRequest();

        // Configuración de la factura
        req.setNumbering_range_id(8); // Este ID te lo da Factus en tu panel
        req.setReference_code(payload.getPedidoId().toString());
        req.setPayment_form(payload.getTipoPago().equalsIgnoreCase("CONTADO") ? 1 : 2);
        req.setPayment_method_code(10); // 10 = Efectivo (ajustar según payload)

        // Mapear Cliente
        CustomerRequest customer = new CustomerRequest();
        customer.setIdentification(payload.getClienteDocumento());
        customer.setNames(payload.getClienteNombre());
        customer.setEmail(payload.getClienteEmail());
        customer.setType_document_identification_id(3); // 3 = Cédula
        customer.setType_organization_id(2);           // 2 = Persona Natural
        customer.setType_regime_id(2);                 // 2 = No responsable de IVA
        customer.setMunicipality_id(983);              // Ejemplo: Cartagena
        req.setCustomer(customer);

        // Mapear Productos
        List<ItemRequest> items = payload.getDetalles().stream().map(d -> {
            ItemRequest item = new ItemRequest();
            item.setCode_reference(d.getProductoId().toString());
            item.setName(d.getProductoNombre());
            item.setQuantity(d.getCantidad());
            item.setPrice(d.getPrecioUnitario()); // Factus calcula el total solo
            item.setTax_rate(19.00);              // IVA 19%
            item.setTributary_id(1);              // 1 = IVA
            item.setUnit_measure_id(70);          // 70 = Unidad
            return item;
        }).collect(Collectors.toList());

        req.setItems(items);
        return req;
    }
}
