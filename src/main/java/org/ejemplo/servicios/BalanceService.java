package org.ejemplo.servicios;

import org.ejemplo.exceptions.BalanceException;
import org.ejemplo.modelos.Balance;
import org.ejemplo.modelos.DetalleFactura;
import org.ejemplo.modelos.Factura;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.modelos.dtos.BalanceDTO;
import org.ejemplo.repository.BalanceRepository;
import org.ejemplo.validations.BalanceValidations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BalanceService {
    private BalanceRepository balanceRepository;
    private FacturaService facturaService;
    private UsersService usersService;

    BalanceService(BalanceRepository balanceRepository, FacturaService facturaService, UsersService usersService){
        this.facturaService = facturaService;
        this.balanceRepository = balanceRepository;
        this.usersService = usersService;
    }

    public BalanceDTO generarBalance(Usuario usuario, Date desde, Date hasta) throws BalanceException {
        BalanceValidations.validarParaCrear(desde, hasta, usersService.retornarUsuarios(), usuario);

        List<Factura> facturas = facturaService.retornarDesdeHasta(desde,hasta);
        String nombre = "from:".concat(desde.toString())
                .concat(" to:")
                .concat(hasta.toString())
                .concat(" from:")
                .concat(usuario.getUser()).concat(". Date:")
                .concat(new Date().toString());
        Balance balance = new Balance();
        balance.setDesde(desde);
        balance.setHasta(hasta);
        balance.setUsuario(usuario);
        balance.setNombre(nombre);
        BalanceDTO balanceDTO =  new BalanceDTO(balance);
        balanceDTO.setFacturas(facturas);
        balanceDTO.setProductoCantidad(productosCantidad(facturas));
        balanceDTO.setCantidadDeProductosVendidos(cantidadProductosVendidos(balanceDTO.getProductoCantidad()));
        balanceDTO.setFacturasPorVendedor(facturasPorVendedor(facturas));
        balanceDTO.setTotalFacturado(totalFacturas(facturas));
        balanceRepository.save(balance);
        return balanceDTO;

    }

    private Map<String, Integer> productosCantidad(List<Factura> facturas){
        Map<String, Integer> productosCantidad = new HashMap<>();
        for(Factura factura : facturas){
            for (DetalleFactura detalleFactura: factura.getDetalles()){
                String producto = detalleFactura.getProducto().getCodigo();
                if (productosCantidad.containsKey(producto)){
                    Integer cantidadProducto = productosCantidad.get(producto);
                    productosCantidad.put(producto, cantidadProducto+detalleFactura.getCantidad());
                } else {
                    productosCantidad.put(detalleFactura.getProducto().getCodigo(),detalleFactura.getCantidad());
                }
            }
        }
        return productosCantidad;
    }

    private Integer cantidadProductosVendidos(Map<String, Integer> productoCantidad){
        Integer cantidad = 0;
        for (Map.Entry<String, Integer> entry : productoCantidad.entrySet()) {
            cantidad+=entry.getValue();
        }
        return cantidad;
    }

    private Map<String, Integer> facturasPorVendedor(List<Factura> facturas){
        Map<String, Integer> facturasPorVendedor = new HashMap<>();
        for(Factura factura : facturas){
            String vendedor = factura.getVendedor().getUser();
            if (facturasPorVendedor.containsKey(vendedor)){
                Integer cantidad = facturasPorVendedor.get(vendedor);
                facturasPorVendedor.put(vendedor, cantidad+1);
            } else {
                facturasPorVendedor.put(vendedor, 1);
            }
        }
        return facturasPorVendedor;
    }

    private Double totalFacturas(List<Factura> facturas){
        Double total = 0.0;
        for (Factura factura: facturas){
            for (DetalleFactura detalle : factura.getDetalles()){
                total+= detalle.getPrecioTotal();
            }
        }
        return total;
    }
}
