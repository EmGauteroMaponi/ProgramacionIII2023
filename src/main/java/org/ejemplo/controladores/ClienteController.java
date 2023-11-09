package org.ejemplo.controladores;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.ClientException;
import org.ejemplo.modelos.Cliente;
import org.ejemplo.servicios.AuthenticationService;
import org.ejemplo.servicios.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@Slf4j
public class ClienteController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    public ClienteService service;

    @PostMapping("/cliente/registry")
    public ResponseEntity<String> createUser(@RequestHeader String token, @RequestBody Cliente cliente){
        try{
            authenticationService.validarToken(token);
            String respuesta = service.guardarCliente(cliente);
            log.info("Usuario creado de forma correcta {}", cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (ClientException e){
            log.warn("No se esta cumpliendo con las validaciones. Usuario a crear: {}", cliente);
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @GetMapping("/cliente/getAll")
    public ResponseEntity<?> getAll(@RequestHeader String token){
        try {
            authenticationService.validarToken(token);
            return ResponseEntity.ok(service.retornarUsuarios());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @GetMapping("/cliente/findById/{id}")
    public ResponseEntity<?> getAll(@RequestHeader String token,@PathVariable(value = "id") Integer id){
        try {
            authenticationService.validarToken(token);
            return ResponseEntity.ok(service.findById(id));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<String> delete(@RequestHeader String token,@PathVariable(value = "id") Integer id){
        try {
            authenticationService.validarToken(token);
            service.borrarUsuarios(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
