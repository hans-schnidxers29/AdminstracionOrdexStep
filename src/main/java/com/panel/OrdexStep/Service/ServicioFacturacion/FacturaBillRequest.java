package com.panel.OrdexStep.Service.ServicioFacturacion;

import lombok.Data;
import java.util.List;

@Data
public class FacturaBillRequest {

    private Integer numbering_range_id;
    private String reference_code;
    private String observation;
    private String payment_form;       // "1" = Contado, "2" = Crédito
    private String payment_due_date;   // "YYYY-MM-DD"
    private String payment_method_code; // "10" = Efectivo, "42" = Débito, etc.

    private Customer customer;
    private List<Item> items;

    @Data
    public static class Customer {
        private String identification;
        private String dv;
        private String company;
        private String trade_name;
        private String names;
        private String address;
        private String email;
        private String phone;
        private String legal_organization_id; // "1"=Persona Jurídica, "2"=Natural
        private String tribute_id;            // "21"=No responsable IVA
        private String identification_document_id; // "6"=NIT, "3"=CC
        private String municipality_id;       // Código del municipio
    }

    @Data
    public static class Item {
        private String code_reference;
        private String name;
        private Integer quantity;
        private Double discount_rate;
        private Double price;
        private String tax_rate;        // "19.00", "5.00", "0.00"
        private Integer unit_measure_id; // 70 = Unidad
        private Integer standard_code_id; // 1 = UNSPSC
        private Integer is_excluded;    // 0 = no excluido
        private Integer tribute_id;     // 1 = IVA
    }
}