package com.panel.OrdexStep.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class documentoSoporteBillRequest {

    private String  reference_code;
    private Provider provider;
    private List<Items> items;
    private Long numering_range_code;
    private String observation;
    private String payment_method_code;

    @Data
    public static class Items {
        private String code_reference;
        private String name;
        private Integer quantity;
        private Double discount_rate;
        private Integer unit_measure_id;
        private Integer standard_item_identification_id;
        private Integer is_excluded;
        private Integer tributary_id;
        private Double tax_rate;
        private Double price;
    }

    public static class Provider{
        private Long numering_document_id ;
        private Integer Identification;
        private Long dv_id;
        private String trade_name;
        private String names;
        private String address;
        private String email;
        private String phone;
        private Long Is_resident;
        private String country_code;
        private Long municipality_id;

    }
}
