package com.panel.OrdexStep.DTOS;

import lombok.Data;

@Data
public class CustomerRequest {
    private String identification;
    private Integer dv; // Dígito de verificación (solo si es NIT)
    private Integer type_document_identification_id;
    private Integer type_organization_id;
    private Integer type_regime_id;
    private String names;
    private String email;
    private String phone;
    private String address;
    private Integer municipality_id;
}