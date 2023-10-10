package org.ejemplo.validations;

import org.ejemplo.exceptions.UserException;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.utils.Utils;
import org.springframework.http.HttpStatus;

import java.util.List;

public class UserValidations implements ValidationsInterface<Usuario, String, Object>{

    @Override
    public void validateToCreate(List<Usuario> usuarios,Usuario usuario, Object object) throws UserException {
        validateUserName(usuario.getUser());
        validateUserDoesNotExist(usuarios, usuario.getUser());
        validateUserRole(usuario.getRole());
    }

    private void validateUserRole(String role) throws UserException {
        if (Utils.validateStringNotEmptyAndNotNull(role)
                || (!role.equalsIgnoreCase("administrador")
                && !role.equalsIgnoreCase("vendedor"))){
            throw new UserException(HttpStatus.PRECONDITION_FAILED, "No se puede ingresar el usuario " + role, "Porque el rol es incorrecto");
        }
    }

    @Override
    public void validateToUpdate() {
        throw new RuntimeException("Estamos utilizando un método para el cual no hay una implementación");
    }

    @Override
    public void validateToDelete(List<Usuario> usuarios, String user) throws UserException{
        if (!Utils.validateStringNotEmptyAndNotNull(user)){
            throw new UserException(HttpStatus.PRECONDITION_FAILED,"No se puede eliminar el usuario", "El usuario no está registrado en nuestra base de datos");
        }
    }

    private void validateUserName(String user) throws UserException {
        if (Utils.validateStringNotEmptyAndNotNull(user)){
            throw new UserException(HttpStatus.PRECONDITION_FAILED,"Error en el campo usuario", "No se permite valor nulo");
        }
    }

    private void validateUserDoesNotExist(List<Usuario> usuarios, String user) throws UserException {
        if(Utils.exists(usuarios, u -> u.getUser().equals(user))){
            throw new UserException(HttpStatus.PRECONDITION_FAILED, "No se puede ingresar el usuario " + user, "El usuario ya se encuentra registrado");
        }
    }
}

