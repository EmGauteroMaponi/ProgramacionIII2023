package org.ejemplo.controladores;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.ClientException;
import org.ejemplo.exceptions.ProductoException;
import org.ejemplo.exceptions.UserException;
import org.ejemplo.modelos.DetalleFactura;
import org.ejemplo.modelos.Factura;
import org.ejemplo.servicios.*;
import org.ejemplo.validations.FacturaValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class FacturaController {

    @Autowired
    public FacturaService service;
    @Autowired
    public ProductoService productoService;
    @Autowired
    public DetalleFacturaService detalleFacturaService;
    @Autowired
    public UsersService usersService;
    @Autowired
    public ClienteService clienteService;


    @PostMapping("/factura/create")
    public ResponseEntity<String> create (@RequestBody Factura factura){
        try{
            FacturaValidations facturaValidations = new FacturaValidations(productoService, detalleFacturaService, clienteService, usersService);
            List<Factura> facturas = service.retornar();
            facturaValidations.validateToCreate(factura, facturas);
            String respuesta = service.guardar(factura);
            log.info("Producto creado de forma correcta {}", factura);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (UserException e){
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (ProductoException e){
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (ClientException e){
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @GetMapping("/factura/getAll")
    public ResponseEntity<List<Factura>> getAllProducts(){
        return ResponseEntity.ok(service.retornar());
    }

}
