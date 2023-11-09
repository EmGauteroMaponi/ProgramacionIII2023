package org.ejemplo.validations;

import org.ejemplo.exceptions.BalanceException;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.utils.Utils;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class BalanceValidations {

    public static void validarParaCrear(Date desde, Date hasta, List<Usuario> usuarios, Usuario user) throws BalanceException {
        if (!Utils.exists(usuarios, usuario -> usuario.getUser().equals(user.getUser()))){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "Usuaio invalido", "El usuario no está registrado en nuestro sistema");
        }
        if (desde == null){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "Fecha invalida", "Debe ingresar fecha desde");
        }
        if (hasta == null){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "Fecha invalida", "Debe ingresar fecha hasta");
        }
        if (desde.after(hasta)){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "Fecha invalida", "La fecha desde no puede ser posterior a la fehca hasta");
        }
    }
}
