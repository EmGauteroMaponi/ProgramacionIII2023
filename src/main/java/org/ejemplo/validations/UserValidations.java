package org.ejemplo.validations;

import org.ejemplo.exceptions.UserException;
import org.ejemplo.modelos.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public class UserValidations {
    public static Boolean validateExistUser(List<Usuario> usuarios, String username){
        for(Usuario user: usuarios){
            if (user.getUser().equals(username)){
                return true;
            }
        }
        return false;
    }

    public static void validateUserForRegister(List<Usuario> usuarios, Usuario usuario) throws UserException {
        if (validateStringNotEmptyNotNull(usuario.getUser())){
            throw new UserException(HttpStatus.PRECONDITION_FAILED,"Error en el campo usuario", "No se permite valor nulo");
        }

        if(validateExistUser(usuarios, usuario.getUser())){
            throw new UserException(HttpStatus.PRECONDITION_FAILED, "No se puede ingresar el usuario " + usuario.getUser(), "El usuario ya se encuentra registrado");
        }
    }

    private static boolean validateStringNotEmptyNotNull(String string) {
        return string == null || string.isBlank();
    }
}

