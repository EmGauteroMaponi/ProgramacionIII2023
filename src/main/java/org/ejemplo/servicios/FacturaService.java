package org.ejemplo.servicios;

import org.ejemplo.exceptions.FacturaException;
import org.ejemplo.modelos.Factura;
import org.ejemplo.repository.FacturaRepository;
import org.ejemplo.validations.FacturaValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository repository;

    public Factura crearFactura(Factura factura) throws FacturaException {
        FacturaValidations.validateFacturaToSave(repository, factura);
        return repository.save(factura);
    }

    public List<Factura> getByVendedorUser(String vendedor){
        return repository.findByVendedor_user(vendedor);
    }

    public List<Factura> getByVendedorRole(String vendedorRole){
        return repository.findByVendedor_role(vendedorRole);
    }

    public List<Factura> findByPrecioGreaterThan(Double precio){
        return repository.findByPrecioGreaterThan(precio);
    }
}
