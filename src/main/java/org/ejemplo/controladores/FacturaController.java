package org.ejemplo.controladores;

import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.FacturaException;
import org.ejemplo.modelos.Factura;
import org.ejemplo.servicios.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class FacturaController {
    @Autowired
    private FacturaService service;

    @PostMapping("/factura/save")//guarda la factura indicada
    public ResponseEntity crearFactura(@RequestBody Factura factura){
        try{
            return ResponseEntity.status(201).body(service.crearFactura(factura));
        }catch (FacturaException e){
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        }
        catch (Exception e){
            log.error("Error guardando la factura {}", factura, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @GetMapping("/factura/getByVendedor/user/{user}")// muestra todas las facturas que el vendedor tenga el user indicado
    public ResponseEntity<List<Factura>> getByVendedorUser(@PathVariable(value="user") String vendedorUser){
        return ResponseEntity.status(200).body(service.getByVendedorUser(vendedorUser));
    }

    @GetMapping("/factura/getByVendedor/role/{role}") //muestra todas las facturas que el vendedor tenga el rol indicado
    public ResponseEntity<List<Factura>> getByVendedorRole(@PathVariable(value="role") String vendedorRole){
        return ResponseEntity.status(200).body(service.getByVendedorRole(vendedorRole));
    }

    @GetMapping("/factura/getByPrecioGreaterThan/{precio}") //Obtiene todas las facturas que tengan un precio mayor al indicado
    public ResponseEntity<List<Factura>> getByPrecio(@PathVariable(value="precio") Double precio){
        return ResponseEntity.status(200).body(service.findByPrecioGreaterThan(precio));
    }
}
