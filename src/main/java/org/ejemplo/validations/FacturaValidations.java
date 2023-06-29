package org.ejemplo.validations;

import org.ejemplo.exceptions.FacturaException;
import org.ejemplo.exceptions.UserException;
import org.ejemplo.modelos.Factura;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.repository.FacturaRepository;
import org.springframework.http.HttpStatus;

import java.util.List;

public class FacturaValidations {
    public static void validateFacturaToSave(FacturaRepository repository, Factura factura) throws FacturaException {
        if (repository.existsById(factura.getId())){
            throw new FacturaException(HttpStatus.PRECONDITION_FAILED,"Error al Guardar la factura","El Id de la factura ya existe");
        }
    }
}

