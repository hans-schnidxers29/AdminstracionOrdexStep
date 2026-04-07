package com.panel.OrdexStep.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class NotaCreditoDto {

    @JsonProperty("billId") // Coincide con el JSON del ERP
    private Long billId;

    @JsonProperty("referenceCode")
    private String referenceCode;

    @JsonProperty("correctionConceptCode") // Cambiado para nivelar con el JSON que mostraste
    private Integer correctionConcept;

    @JsonProperty("paymentMethodCode") // Coincide con el JSON del ERP
    private Long MetodoPago;

    @JsonProperty("observation") // Para capturar la justificación
    private String observation;

    @JsonProperty("numberingRangeId")
    private Long range_numeracion;

    private String number_range;

    private List<Item> items;

    private Double priceNew ;

    @Data
    public static class Item {
        private String code_reference;
        private String name;
        private Integer quantity;
        private Double discount_rate;
        private Double price;
        private String tax_rate;
        private Integer unit_measure_id;
        private Integer standard_code_id;
        private Integer is_excluded;
        private Integer tribute_id;
    }
}