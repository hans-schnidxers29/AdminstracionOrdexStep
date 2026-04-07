package com.panel.OrdexStep.Service.ServicioFacturacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FactusRangosService {

    private final String URL_RANGOS = "https://api-sandbox.factus.com.co/v1/numbering-ranges";

    private final String URL_LOGO_EMPRESA ="https://api-sandbox.factus.com.co/v1/company/logo";

    @Autowired
    private FactusAuthService authService;

    private final RestTemplate restTemplate = new RestTemplate();

    public Object getRangos() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getAccessToken());
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                URL_RANGOS, HttpMethod.GET, entity, Object.class
        );
        return response.getBody();
    }

    public Object actualizarLogoFactus(MultipartFile imagenPostman) throws IOException {
        // 1. Configurar Headers según la documentación
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getAccessToken());
        headers.set("Accept", "application/json");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 2. Convertir el MultipartFile a un recurso que se pueda enviar por red
        // Usamos ByteArrayResource y sobrescribimos getFilename para que Factus sepa el nombre del archivo
        ByteArrayResource recursoImagen = new ByteArrayResource(imagenPostman.getBytes()) {
            @Override
            public String getFilename() {
                return imagenPostman.getOriginalFilename();
            }
        };
        System.out.println("dentro del metodo");
        // 3. Preparar el Body (Form-Data)
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", recursoImagen); // "image" es el nombre exacto que pide la documentación

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        // 5. Enviar petición POST
        return restTemplate.postForEntity(URL_LOGO_EMPRESA, entity, Object.class);
    }
}