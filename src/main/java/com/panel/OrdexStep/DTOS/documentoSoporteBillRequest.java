package com.panel.OrdexStep.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class documentoSoporteBillRequest {

    private String reference_code;
    private Integer numbering_range_id;
    private String payment_method_code;
    private String observation;
    private Provider provider;
    private List<Item> items;

    @Data
    public static class Provider {
        private Long identification_document_id;
        private String identification;
        private Long dv;
        private String trade_name;
        private String names;
        private String address;
        private String email;
        private String phone;
        private int is_residente;
        private String country_code;
        private Long municipality_id;
    }

    @Data
    public static class Item {
        private String code_reference;
        private String name;
        private Integer quantity;
        private Double discount_rate;
        private Integer unit_measure_id;
        private Integer standard_code_id;
        private int is_excluded;
        private int tribute_id;
        private String tax_rate;
        private Double price;
    }
}