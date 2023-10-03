package org.ejemplo.servicios;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.ClientException;
import org.ejemplo.modelos.DetalleFactura;
import org.ejemplo.modelos.Producto;
import org.ejemplo.repository.DetalleFacturaRepository;
import org.ejemplo.repository.ProductoRepository;
import org.ejemplo.validations.DetalleFacturaValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DetalleFacturaService {
    @Autowired
    DetalleFacturaRepository detalleFacturaRepository;

    @Autowired
    ProductoRepository productoRepository;
    public String guardar(DetalleFactura detalleFactura) throws ClientException {
        DetalleFacturaValidations.validateForCreate(productoRepository,detalleFacturaRepository.findAll(), detalleFactura);

        return saveDetalle(detalleFactura);
    }


    public String actualizar(DetalleFactura detalleFactura) throws ClientException {
        DetalleFacturaValidations.validateForUpdate(productoRepository, detalleFacturaRepository.findAll(), detalleFactura);
        return saveDetalle(detalleFactura);
    }

    public List<DetalleFactura> retornar(){
        return detalleFacturaRepository.findAll();
    }

    public void borrar(Integer id) {
        detalleFacturaRepository.deleteById(id);
    }

    private String saveDetalle(DetalleFactura detalleFactura) {
        Optional<Producto> optionalProducto = productoRepository.findById(detalleFactura.getProducto().getCodigo());
        Double precio = optionalProducto.get().getPrecio();
        detalleFactura.setPrecioUnitario(precio);
        detalleFactura.setPrecioTotal(precio * detalleFactura.getCantidad());
        detalleFacturaRepository.save(detalleFactura);
        return "Detalle cargado correctamente";
    }
}
