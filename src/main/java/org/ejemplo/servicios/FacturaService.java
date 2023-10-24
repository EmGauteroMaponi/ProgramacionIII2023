package org.ejemplo.servicios;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.ValidationException;
import org.ejemplo.modelos.Factura;
import org.ejemplo.repository.FacturaRepository;
import org.ejemplo.validations.FacturaValidations;
import org.ejemplo.validations.ValidationsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FacturaService {
    private static ValidationsInterface<Factura, Integer, Map<String, List>> validations = new FacturaValidations();
    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    UsersService usersService;
    @Autowired
    ClienteService clienteService;

    public String guardar(Factura factura) throws ValidationException {
        Map<String, List> additionalData = new HashMap<>();
        additionalData.put("clientes", clienteService.retornarUsuarios());
        additionalData.put("usuarios", usersService.retornarUsuarios());
        validations.validateToCreate(facturaRepository.findAll(), factura, additionalData);
        facturaRepository.save(factura);
        return "ok";
    }

    public List<Factura> retornar(){
        return facturaRepository.findAll();
    }

}
