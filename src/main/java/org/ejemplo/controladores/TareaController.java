package org.ejemplo.controladores;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.BalanceException;
import org.ejemplo.modelos.Autentication;
import org.ejemplo.modelos.Tarea;
import org.ejemplo.servicios.AuthenticationService;
import org.ejemplo.servicios.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@Slf4j
public class TareaController {

    public TareaService service;
    private AuthenticationService authenticationService;

    @Autowired
    public TareaController(TareaService tareaService, AuthenticationService authenticationService){
        this.service = tareaService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/tarea/create")
    public ResponseEntity<?> create (@RequestHeader String token, @RequestBody Tarea tarea){
        try{
            Autentication autentication = authenticationService.validarToken(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crearTarea(autentication.getUser(), tarea));
        } catch (BalanceException e){
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @PutMapping("/tarea/update")
    public ResponseEntity<?> update (@RequestHeader String token, @RequestBody Tarea tarea){
        try{
            Autentication autentication = authenticationService.validarToken(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizarTarea(autentication.getUser(), tarea));
        } catch (BalanceException e){
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @PostMapping("/tarea/finalizar/{id}")
    public ResponseEntity<?> finalizar (@RequestHeader String token, @PathVariable(value = "id") String id){
        try{
            Autentication autentication = authenticationService.validarToken(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.cerrarTarea(autentication.getUser(), id));
        } catch (BalanceException e){
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }
    @GetMapping("/tarea/obtener/por/encargado/{user}")
    public ResponseEntity<?> findByEncargado (@RequestHeader String token, @PathVariable(value = "user") String user){
        try{
            Autentication autentication = authenticationService.validarToken(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.findByEncargado(user));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }
    @GetMapping("/tarea/obtener/por/autor/{user}")
    public ResponseEntity<?> findByAutor (@RequestHeader String token, @PathVariable(value = "user") String user){
        try{
            authenticationService.validarToken(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.findByAutor(user));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }
    @GetMapping("/tarea/obtener/todos")
    public ResponseEntity<?> findAll (@RequestHeader String token){
        try{
            authenticationService.validarToken(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.obtenerTodos());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }
}
