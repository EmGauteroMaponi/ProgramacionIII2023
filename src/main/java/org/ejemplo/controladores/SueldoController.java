package org.ejemplo.controladores;

import org.ejemplo.exceptions.SueldoException;
import org.ejemplo.modelos.Autentication;
import org.ejemplo.modelos.Sueldo;
import org.ejemplo.servicios.AuthenticationService;
import org.ejemplo.servicios.SueldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
public class SueldoController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private SueldoService service;


    @PostMapping("/sueldo/create")
    public ResponseEntity crearSueldo(@RequestHeader(value = "token") String token , @RequestBody Sueldo sueldo){
        try{
            Autentication autentication = authenticationService.validarToken(token);
            return ResponseEntity.ok(service.crearSueldo(sueldo, autentication.getUser()));
        }catch (SueldoException e){
            return ResponseEntity.internalServerError().body("UPs");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/sueldo/update")
    public ResponseEntity actualizarSueldo(@RequestHeader(value = "token") String token, @RequestBody Sueldo sueldo){
        try{
            Autentication autentication = authenticationService.validarToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/sueldo/findByUser")
    public ResponseEntity buscarSueldo(@RequestHeader(value = "token") String token,@PathVariable(value = "id") Integer id){
        try{
            Autentication autentication = authenticationService.validarToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
