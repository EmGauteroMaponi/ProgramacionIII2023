package org.ejemplo.controladores;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.DetalleFacturaException;
import org.ejemplo.modelos.Autentication;
import org.ejemplo.modelos.DetalleFactura;
import org.ejemplo.servicios.AuthenticationService;
import org.ejemplo.servicios.DetalleFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class DetalleFacturaController {
    @Autowired
    public DetalleFacturaService service;
    private AuthenticationService authenticationService;

    @PostMapping("/detalleFactura/create")
    public ResponseEntity<?> createProducto(@RequestHeader String token,@RequestBody DetalleFactura detalleFactura){
        try{
            Autentication autentication = authenticationService.validarToken(token);
            DetalleFactura respuesta = service.guardar (autentication.getUser(), detalleFactura);
            log.info("Producto creado de forma correcta {}", detalleFactura.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (DetalleFacturaException e){
            log.warn("No se esta cumpliendo con las validaciones. Cliente a crear: {}", detalleFactura);
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @GetMapping("/detalleFactura/getAll")
    public ResponseEntity<List<DetalleFactura>> getAllProducts(){
        return ResponseEntity.ok(service.retornar());
    }

    @PostMapping("/detalleFactura/update")
    public ResponseEntity<?> updateProducto(@RequestHeader String token,@RequestBody DetalleFactura detalleFactura){
        try{
            Autentication autentication = authenticationService.validarToken(token);
            DetalleFactura respuesta = service.actualizar (autentication.getUser(), detalleFactura);
            log.info("Producto creado de forma correcta {}", detalleFactura.getId());
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } catch (DetalleFacturaException e){
            log.warn("No se esta cumpliendo con las validaciones. Producto a crear: {}", detalleFactura);
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }
    @DeleteMapping("/detalleFactura/delete/{codigo}")
    public ResponseEntity<String> deleteProducto(@PathVariable(value = "codigo") Integer id){
        try{
            service.borrar (id);
            log.info("Producto borrado de forma correcta {}", id);
            return ResponseEntity.status(HttpStatus.OK).body("Producto Borraddo de Forma Correcta");
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }
}
