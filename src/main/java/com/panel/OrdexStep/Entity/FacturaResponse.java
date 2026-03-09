package com.panel.OrdexStep.Entity;

import lombok.Data;

import java.util.List;

@Data
public class FacturaResponse {
    private boolean status;
    private String message;
    private DataResponse data;

    @Data
    public static class DataResponse {
        private String bill_number; // Número de factura DIAN
        private String cufe;        // Código Único de Factura Electrónica
        private String qr;          // Texto para generar el código QR
        private String url_pdf;     // Link para ver la factura
        private String url_xml;     // Archivo técnico
        private List<String> errors; // Errores si la DIAN rechaza
    }
}
