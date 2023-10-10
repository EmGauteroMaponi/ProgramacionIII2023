package org.ejemplo.validations;

import org.ejemplo.exceptions.FacturaException;
import org.ejemplo.modelos.Cliente;
import org.ejemplo.modelos.Factura;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.utils.Utils;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FacturaValidations implements ValidationsInterface<Factura,Integer, Map<String, List>>{

    @Override
    public void validateToCreate(List<Factura> facturas, Factura facturaToSave, Map<String, List> additionalData) throws FacturaException {
        List <Cliente> clients = (List<Cliente>) additionalData.get("clientes");
        List <Usuario> users = (List<Usuario>) additionalData.get("usuarios");

        if (Objects.isNull(facturaToSave)){
            throw new FacturaException(HttpStatus.PRECONDITION_FAILED, "Factura Invalida", "La factura no puede ser nula");
        }
        validateDoesNotExist(facturas,facturaToSave);

        if (Objects.isNull(facturaToSave.getVendedor())){
            throw new FacturaException(HttpStatus.PRECONDITION_FAILED, "El vendedor no existe", "La factura debe contener un vendedor");
        }
        if (!facturaToSave.getVendedor().getRole().equalsIgnoreCase("vendedor")){
            throw new FacturaException(HttpStatus.PRECONDITION_FAILED, "Vendedor invalido", String.format("El rol: %s  es invalido para crear una factura", facturaToSave.getVendedor().getRole()));
        }
        if (!Utils.exists(users, user -> user.getUser().equals(facturaToSave.getVendedor().getUser()))){
            throw new FacturaException(HttpStatus.PRECONDITION_FAILED, "Vendedor invalido", "El vendedor no está registrado en nuestro sistema");
        }
        if (facturaToSave.getComprador() != null &&
                !Utils.exists(clients, client -> client.getId().equals(facturaToSave.getComprador().getId()))){
            throw new FacturaException(HttpStatus.PRECONDITION_FAILED, "Comprador invalido", "El comprador no está registrado en nuestro sistema");
        }
    }

    @Override
    public void validateToUpdate() {
        throw new RuntimeException("La funcionalidad invocada no está desarrollada para este módulo");
    }

    @Override
    public void validateToDelete(List<Factura> obtectList, Integer keyToCreate) {
        throw new RuntimeException("La funcionalidad invocada no está desarrollada para este módulo");
    }

    private void validateDoesNotExist(List<Factura> facturas, Factura facturaToSave) throws FacturaException {
        if (Utils.exists(facturas, factura -> factura.getNro1().equals(facturaToSave.getNro1())&& factura.getNro2().equals(facturaToSave.getNro2()))){
            throw new FacturaException(HttpStatus.PRECONDITION_FAILED, "La factura ingresada ya está registrada", "La numeración de la factura a guardar ya está registrada previamente");
        }
    }
}
