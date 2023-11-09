package org.ejemplo.controladores;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.BalanceException;

import org.ejemplo.modelos.Usuario;
import org.ejemplo.modelos.dtos.BalanceDTO;
import org.ejemplo.servicios.BalanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
public class BalanceController {

    public BalanceService service;

    public BalanceController(BalanceService balanceService){
        this.service = balanceService;
    }


    @PostMapping("/balance/generate/{user}")
    public ResponseEntity<?> create (@PathVariable(value = "user") String user, @RequestBody Map<String, Date> parameters){
        try{
            BalanceDTO balanceDTO= service.generarBalance(new Usuario(user, "",""),parameters.get("desde"),parameters.get("hasta"));
            return ResponseEntity.status(HttpStatus.CREATED).body(balanceDTO);
        } catch (BalanceException e){
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }
}
