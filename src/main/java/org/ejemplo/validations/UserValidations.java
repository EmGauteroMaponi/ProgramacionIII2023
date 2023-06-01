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

    public static void validateUserForRegister(List<Usuario> usuarios, String username) throws UserException {
        if(validateExistUser(usuarios, username)){
            throw new UserException(HttpStatus.PRECONDITION_FAILED, "No se puede ingresar el usuario " + username, "El usuario ya se encuentra registrado");
        }
    }
}

