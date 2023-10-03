package org.ejemplo.validations;

import org.ejemplo.exceptions.ClientException;
import org.ejemplo.exceptions.ProductoException;
import org.ejemplo.exceptions.UserException;
import org.ejemplo.modelos.Factura;
import org.ejemplo.servicios.ClienteService;
import org.ejemplo.servicios.DetalleFacturaService;
import org.ejemplo.servicios.ProductoService;
import org.ejemplo.servicios.UsersService;
import org.springframework.http.HttpStatus;

import java.util.List;

public class FacturaValidations {
    ProductoService productoService;
    DetalleFacturaService detalleFacturaService;
    UsersService usersService;
    ClienteService clienteService;

    public FacturaValidations(ProductoService productoService, DetalleFacturaService detalleFacturaService, ClienteService clienteService, UsersService usersService){
        this.productoService = productoService;
        this.detalleFacturaService = detalleFacturaService;
        this.clienteService = clienteService;
        this.usersService = usersService;
    }

    public void validateToCreate(Factura facturaToSave, List<Factura> facturas) throws UserException, ProductoException, ClientException {
        if (facturaToSave == null){
            throw new UserException(HttpStatus.PRECONDITION_FAILED, "Factura Invalida", "La factura no puede ser nula");
        }
        for(Factura factura:facturas){
            if (factura.getNro1().equals(facturaToSave.getNro1())&& factura.getNro2().equals(facturaToSave.getNro2())){
                throw new UserException(HttpStatus.PRECONDITION_FAILED, "La factura ingresada ya está registrada", "La numeración de la factura a guardar ya está registrada previamente");
            }
        }
        if (facturaToSave.getVendedor() == null){
            throw new UserException(HttpStatus.PRECONDITION_FAILED, "El vendedor no existe", "La factura debe contener un vendedor");
        }
        if (!facturaToSave.getVendedor().getRole().equalsIgnoreCase("vendedor")){
            throw new ProductoException(HttpStatus.PRECONDITION_FAILED, "Vendedor invalido", String.format("El rol: %s  es invalido para crear una factura", facturaToSave.getVendedor().getRole()));
        }
        if (!usersService.retornarUsuarios().contains(facturaToSave.getVendedor())){
            throw new UserException(HttpStatus.PRECONDITION_FAILED, "Vendedor invalido", "El vendedor no está registrado en nuestro sistema");
        }
        if (facturaToSave.getComprador() != null &&
                !clienteService.retornarUsuarios().contains(facturaToSave.getComprador())){
            throw new ClientException(HttpStatus.PRECONDITION_FAILED, "Comprador invalido", "El comprador no está registrado en nuestro sistema");
        }
    }
}
