package org.ejemplo.modelos.dtos;

import lombok.Data;
import org.ejemplo.modelos.Balance;
import org.ejemplo.modelos.Factura;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class BalanceDTO {
    private Date desde;
    private Date hasta;
    private String nombre;
    private List<FacturaDTO> facturas;
    private Double totalFacturado;
    private Integer cantidadDeProductosVendidos;
    private Map<String, Integer> facturasPorVendedor;
    private Map<String, Integer> productoCantidad;

    public BalanceDTO(Balance balance){
        this.nombre = balance.getNombre();
        this.desde = balance.getDesde();
        this.hasta = balance.getHasta();
    }
}
