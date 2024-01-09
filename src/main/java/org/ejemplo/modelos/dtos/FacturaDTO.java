package org.ejemplo.modelos.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ejemplo.modelos.Cliente;
import org.ejemplo.modelos.DetalleFactura;
import org.ejemplo.modelos.Factura;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacturaDTO {
    private Integer id;
    private String nro;
    private Cliente comprador;
    private UsuarioDTO vendedor;
    private Date fecha;
    private Double total;
    private List<DetalleFactura> detalles;

    public FacturaDTO(Factura factura) {
        this.id = factura.getId();
        this.detalles = factura.getDetalles();
        this.comprador = factura.getComprador();
        this.vendedor = new UsuarioDTO(factura.getVendedor());
        this.fecha = factura.getFecha();
        this.nro = String.format("%s-%s", factura.getNro1(),factura.getNro2());
        this.total = calculateTotal();
    }
    private Double calculateTotal(){
        Double total= 0.0;
        for(DetalleFactura detalle: this.detalles){
            total += detalle.getPrecioTotal();
        }
        return total;
    }
}
