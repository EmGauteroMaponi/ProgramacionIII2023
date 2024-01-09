package org.ejemplo.controladores;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.UserException;
import org.ejemplo.modelos.dtos.LogDTO;
import org.ejemplo.modelos.Login;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.servicios.AuthenticationService;
import org.ejemplo.servicios.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@Slf4j
public class UsuarioController {
    @Autowired
    public UsersService service;
    @Autowired
    public AuthenticationService authenticationService;

    @PostMapping("/usuario/registry")
    public ResponseEntity<String> createUser(@RequestBody Usuario usuario){
        try{
            String respuesta = service.guardarUsuario(usuario);
            log.info("Usuario creado de forma correcta {}", usuario.getUser());
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (UserException e){
            log.warn("No se esta cumpliendo con las validaciones. Usuario a crear: {}", usuario);
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @GetMapping("/usuario/getAll")
    public ResponseEntity<?> getAll(@RequestHeader String token){
        try {
            authenticationService.validarToken(token);
            return ResponseEntity.ok(service.retornarUsuarios());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/usuario/login")
    public ResponseEntity<LogDTO> login(@RequestBody Login login) {
        try {
            return ResponseEntity.ok(service.login(login));
        } catch (UserException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    @PostMapping("/usuario/logout")
    public ResponseEntity<String> login(@RequestHeader(value = "token") String token) {
        try {
            authenticationService.eliminarToken(token);
            return ResponseEntity.ok("Se ha cerrado session de forma correcta");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }
}
