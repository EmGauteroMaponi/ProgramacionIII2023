package org.ejemplo.servicios;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.ValidationException;
import org.ejemplo.modelos.DetalleFactura;
import org.ejemplo.modelos.Factura;
import org.ejemplo.modelos.dtos.FacturaDTO;
import org.ejemplo.repository.FacturaRepository;
import org.ejemplo.validations.FacturaValidations;
import org.ejemplo.validations.ValidationsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FacturaService {
    private static ValidationsInterface<Factura, Integer, Map<String, List>> validations = new FacturaValidations();
    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private UsersService usersService;
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private DetalleFacturaService detalleFacturaService;

    public String guardar(String user, Factura factura) throws ValidationException {
        Map<String, List> additionalData = new HashMap<>();
        factura.setVendedor(usersService.findByUser(user).orElse(null));
        additionalData.put("clientes", clienteService.retornarUsuarios());
        additionalData.put("usuarios", usersService.retornarUsuarios());
        validations.validateToCreate(facturaRepository.findAll(), factura, additionalData);
        facturaRepository.save(factura);
        return "ok";
    }

    public String guardarFacturaCompleta(String user, Factura factura) throws ValidationException {
        Map<String, List> additionalData = new HashMap<>();
        factura.setVendedor(usersService.findByUser(user).orElse(null));
        additionalData.put("clientes", clienteService.retornarUsuarios());
        additionalData.put("usuarios", usersService.retornarUsuarios());
        validations.validateToCreate(facturaRepository.findAll(), factura, additionalData);
        for (DetalleFactura detalle: factura.getDetalles()){
            detalleFacturaService.guardar(detalle);
        }
        facturaRepository.save(factura);
        return "ok";
    }

    public List<FacturaDTO> retornar(){
        return facturaRepository.findAll().stream().map(f -> new FacturaDTO(f)).collect(Collectors.toList());
    }

    public List<FacturaDTO> retornarDesdeHasta(Date desde, Date hasta){
        return retornar().stream().filter(factura -> factura.getFecha().after(desde) && factura.getFecha().before(hasta)).collect(Collectors.toList());
    }

}
