package com.panel.OrdexStep.DTOS;

import lombok.Data;

@Data
public class ItemRequest {
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
