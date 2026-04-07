package com.panel.OrdexStep.Service.ServicioFacturacion;

import com.panel.OrdexStep.Entity.FactusTokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class FactusAuthService {

    private final RestTemplate restTemplate = new RestTemplate();
    private FactusTokenResponse tokenAlmacenado;

    // Credenciales (Idealmente en application.properties)
    private final String URL_TOKEN = "https://api-sandbox.factus.com.co/oauth/token";
    private final String CLIENT_ID = "a0d5bbb4-447e-48a8-8fb3-f90e1c4e4da4";
    private final String CLIENT_SECRET = "63fK3kGhXzKgrprSAm3ESpGISPcUpIko0wqEKafn";
    private final String USERNAME = "sandbox@factus.com.co";
    private final String PASSWORD = "sandbox2024%";

    /**
     * Obtiene un token válido. Si el que tenemos expiró, pide uno nuevo.
     */
    public String getAccessToken() {
        if (tokenAlmacenado == null || esTokenExpirado()) {
            solicitarNuevoToken();
        }
        return tokenAlmacenado.getAccess_token();
    }

    /**
     * Verifica si el token ya no es válido (con un margen de 1 minuto)
     */
    private boolean esTokenExpirado() {
        return LocalDateTime.now().isAfter(
                tokenAlmacenado.getCreatedAt().plusSeconds(tokenAlmacenado.getExpires_in() - 60)
        );
    }
    /**
     * Realiza la petición POST a Factus para obtener el token
     */
    private void solicitarNuevoToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);
        body.add("username", USERNAME);
        body.add("password", PASSWORD);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<FactusTokenResponse> response = restTemplate.postForEntity(URL_TOKEN, request, FactusTokenResponse.class);
            this.tokenAlmacenado = response.getBody();
            if (this.tokenAlmacenado != null) {
                this.tokenAlmacenado.setCreatedAt(LocalDateTime.now());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al autenticar con Factus: " + e.getMessage());
        }
    }
}
