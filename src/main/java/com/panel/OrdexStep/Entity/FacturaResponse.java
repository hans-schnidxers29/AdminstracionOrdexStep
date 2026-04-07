package com.panel.OrdexStep.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacturaResponse {

    private String status;
    private String message;
    private Object data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataResponse {
        private BillData bill;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BillData {
        private String number;           // Número de factura
        private String cufe;             // Código DIAN
        private String qr;
        private String reference_code;
        @JsonProperty("id")
        private Long id;

    }
}