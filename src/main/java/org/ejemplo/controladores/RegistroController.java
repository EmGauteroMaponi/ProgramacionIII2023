package org.ejemplo.controladores;

import org.ejemplo.modelos.Autentication;
import org.ejemplo.modelos.Registro;
import org.ejemplo.modelos.dtos.RegistroDTO;
import org.ejemplo.servicios.AuthenticationService;
import org.ejemplo.servicios.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
public class RegistroController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private RegistroService service;

    @GetMapping("/registro/getAll")
    public ResponseEntity<?> getAll(@RequestHeader(value = "token") String token){
        try {
            Autentication autentication = authenticationService.validarToken(token);
            return ResponseEntity.ok(service.getAll());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
