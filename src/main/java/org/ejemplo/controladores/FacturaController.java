package org.ejemplo.controladores;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.FacturaException;
import org.ejemplo.modelos.Autentication;
import org.ejemplo.modelos.Factura;
import org.ejemplo.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@Slf4j
public class FacturaController {
    @Autowired
    private AutenticationService autenticationService;

    @Autowired
    public FacturaService service;


    @PostMapping("/factura/create")
    public ResponseEntity<String> create (@RequestHeader String token, @RequestBody Factura factura){
        try{
            Autentication autentication = autenticationService.validarToken(token);
            String respuesta = service.guardar(autentication.getUser(), factura);
            log.info("Producto creado de forma correcta {}", factura);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (FacturaException e){
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @GetMapping("/factura/getAll")
    public ResponseEntity<?> getAll(@RequestHeader String token){
        try{
            autenticationService.validarToken(token);
            return ResponseEntity.ok(service.retornar());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
