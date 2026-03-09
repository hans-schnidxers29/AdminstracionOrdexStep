package com.panel.OrdexStep.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class FacturaERPPayload {
    private Long pedidoId;
    private String clienteDocumento;
    private String clienteNombre;
    private String clienteEmail;
    private String clienteTelefono;
    private String clienteDireccion;
    private Integer municipioId; // ID Dane
    private List<DetallePedidoDTO> detalles;
    private String metodoPago; // "EFECTIVO", "TRANSFERENCIA"
    private String tipoPago; // "CONTADO", "CREDITO"
}