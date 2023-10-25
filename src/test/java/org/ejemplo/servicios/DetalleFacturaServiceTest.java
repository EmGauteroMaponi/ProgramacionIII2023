package org.ejemplo.servicios;

import org.ejemplo.exceptions.ClientException;
import org.ejemplo.modelos.Cliente;
import org.ejemplo.modelos.DetalleFactura;
import org.ejemplo.modelos.Producto;
import org.ejemplo.repository.ClienteRepository;
import org.ejemplo.repository.DetalleFacturaRepository;
import org.ejemplo.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DetalleFacturaServiceTest {

    @Test
    public void testCrearDetalleCorrecto() {
        DetalleFacturaRepository detalleFacturaRepository = Mockito.mock(DetalleFacturaRepository.class);
        ProductoRepository productoRepository = Mockito.mock(ProductoRepository.class);

        Producto prod = new Producto("codigo", "nombre", "descrip", new Date(), 15, 1500.0);
        DetalleFactura df = new DetalleFactura();
        df.setId(1);
        df.setProducto(prod);
        df.setCantidad(15);
        df.setPrecioTotal(3000.0);

        Mockito.when(detalleFacturaRepository.findAll()).thenReturn(List.of());
        Mockito.when(productoRepository.findById("codigo")).thenReturn(Optional.of(prod));

        DetalleFacturaService dtf = new DetalleFacturaService(detalleFacturaRepository, productoRepository);

        assertDoesNotThrow(() -> dtf.guardar(df));

        assertEquals(22500.0, df.getPrecioTotal());
        assertEquals(1500.0, df.getPrecioUnitario());


    }

    @Test
    public void testCrearDetalleConError() {
        DetalleFacturaRepository detalleFacturaRepository = Mockito.mock(DetalleFacturaRepository.class);
        ProductoRepository productoRepository = Mockito.mock(ProductoRepository.class);

        Producto prod = new Producto("codigo", "nombre", "descrip", new Date(), 15, 1500.0);
        DetalleFactura df = new DetalleFactura();
        df.setId(1);
        df.setProducto(prod);
        df.setCantidad(20);

        Mockito.when(detalleFacturaRepository.findAll()).thenReturn(List.of());
        Mockito.when(productoRepository.findById("codigo")).thenReturn(Optional.of(prod));

        DetalleFacturaService dtf = new DetalleFacturaService(detalleFacturaRepository, productoRepository);

        ClientException exception = assertThrows(ClientException.class, () -> {
            dtf.guardar(df);
        });
        assertEquals(HttpStatus.PRECONDITION_FAILED, exception.getStatusCode());
        assertEquals("No se puede ingresar el Detalle ", exception.getMessage());
        assertEquals("La cantidad no puede ser mayo al stock (15)", exception.getCausa());
    }
}