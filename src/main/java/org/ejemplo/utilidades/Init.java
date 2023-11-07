package org.ejemplo.utilidades;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.ClientException;
import org.ejemplo.exceptions.ProductoException;
import org.ejemplo.exceptions.ValidationException;
import org.ejemplo.modelos.*;
import org.ejemplo.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class Init {
    private ProductoService productoService;
    private UsersService usersService;
    private ClienteService clienteService;
    private FacturaService facturaService;
    private BalanceService balanceService;



    private static final Producto coca1 = new Producto("cocaCola1l", "Coca cola 1L", "Gaseosa sabor cola marca coca de un litro",new Date(),100,800.0);
    private static final Producto coca15 = new Producto("cocaCola15l", "Coca cola 1.5L", "Gaseosa sabor cola marca coca de un un litro y medio",new Date(),50,1000.0);
    private static final Producto coca2 = new Producto("cocaCola2l", "Coca cola 2L", "Gaseosa sabor cola marca coca de un dos litros",new Date(),10,1500.0);
    private static final Producto coca25 = new Producto("cocaCola25l", "Coca cola 2.5L", "Gaseosa sabor cola marca coca de dos litros y medio",new Date(),500,2000.0);
    private static final Cliente consumidorFinal =  new Cliente(1,"Consumidor Final", 0L, 0L, null, null);
    private static final Cliente messi =  new Cliente(2,"Lionel Messi", 22123654L, 20221236544L, "San Martin 127, Rosario, Santa Fé, Argentina", "3515296124");
    private static final Cliente cristiano =  new Cliente(3,"Cristiano Ronaldo", 10123456L, 20101234565L, "Sin dirección, Portugal", "123456987");
    private static final Usuario administrador = new Usuario("admin", "Admin123", "administrador");
    private static final Usuario vendedor = new Usuario("vendedor", "Vende123", "vendedor");
    private static final Usuario vendedor1 = new Usuario("vendedor1", "Vende123", "vendedor");



    @Autowired
    public Init(ProductoService productoService, UsersService usersService, ClienteService clienteService, FacturaService facturaService, BalanceService balanceService) {
        this.productoService = productoService;
        this.usersService = usersService;
        this.clienteService = clienteService;
        this.facturaService = facturaService;
        this.balanceService = balanceService;

    }

    @PostConstruct
    public void inicializarProyecto() throws ValidationException {
        log.info("Estamos bien, esto se ejecuta una vez levantado el proyecto.");
        crearUsuarios();
        crearClientes();
        crearProductos();
        crearFactura();
    }

    private void crearUsuarios() throws ValidationException {
        if (usersService.retornarUsuarios().isEmpty()){
            usersService.guardarUsuario(administrador);
            usersService.guardarUsuario(vendedor);
            usersService.guardarUsuario(vendedor1);
        }
    }

    private void crearClientes() throws ClientException {
        if (clienteService.retornarUsuarios().isEmpty()){
            clienteService.guardarCliente(consumidorFinal);
            clienteService.guardarCliente(messi);
            clienteService.guardarCliente(cristiano);
        }
    }

    private void crearProductos() throws ProductoException {
        if (productoService.retornarProductos().isEmpty()){
            productoService.guardarProducto(coca1);
            productoService.guardarProducto(coca15);
            productoService.guardarProducto(coca2);
            productoService.guardarProducto(coca25);
        }
    }

    private void crearFactura() throws ValidationException {
        if (facturaService.retornar().isEmpty()){
            DetalleFactura detalle1 = new DetalleFactura(1,coca1, 15,0.0, 0.0);
            DetalleFactura detalle2 = new DetalleFactura(2,coca15, 5,0.0, 0.0);
            DetalleFactura detalle3 = new DetalleFactura(3,coca25, 100,0.0, 0.0);
            DetalleFactura detalle4 = new DetalleFactura(4,coca25, 2,0.0, 0.0);
            DetalleFactura detalle5 = new DetalleFactura(5,coca2, 1,0.0, 0.0);
            DetalleFactura detalle6 = new DetalleFactura(6,coca1, 20,0.0, 0.0);

            Factura factura = new Factura(1, "001", "0000000001", consumidorFinal, vendedor, new Date(), List.of(detalle1, detalle2));
            Factura factura1 = new Factura(2, "001", "0000000002", messi, vendedor1, new Date(), List.of(detalle3));
            Factura factura2 = new Factura(3, "001", "0000000003", consumidorFinal, vendedor, new Date(), List.of(detalle4));
            Factura factura3 = new Factura(4, "001", "0000000004", cristiano, vendedor1, new Date(), List.of(detalle5, detalle6));

            facturaService.guardarFacturaCompleta(vendedor.getUser(), factura);
            facturaService.guardarFacturaCompleta(vendedor1.getUser(), factura1);
            facturaService.guardarFacturaCompleta(vendedor.getUser(), factura2);
            facturaService.guardarFacturaCompleta(vendedor1.getUser(), factura3);
        }
    }

}
