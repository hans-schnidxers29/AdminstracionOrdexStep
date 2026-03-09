package com.panel.OrdexStep.DTOS;
import lombok.Data;

@Data
public class DetallePedidoDTO {
    private Long productoId;        // ID interno en tu base de datos
    private String productoNombre;  // Nombre que saldrá en la factura
    private Integer cantidad;       // Unidades vendidas
    private Double precioUnitario;  // Precio antes de IVA
    private Double porcenIva;       // Ejemplo: 19.0 para el 19%
    private Double descuento;       // Valor en dinero del descuento (si aplica)
}