package com.panel.OrdexStep.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class FacturaRequest {
    private Integer numbering_range_id;
    private String reference_code;
    private Integer customer_id;
    private Integer payment_form;
    private Integer payment_method_code;
    private String observation;
    private CustomerRequest customer;
    private List<ItemRequest> items;
}
