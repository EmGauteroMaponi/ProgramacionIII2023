package org.ejemplo.controladores;

import org.ejemplo.modelos.Registro;
import org.ejemplo.servicios.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegistroController {
    @Autowired
    private RegistroService service;

    @GetMapping("/registro/getAll")
    public ResponseEntity<List<Registro>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }
}
