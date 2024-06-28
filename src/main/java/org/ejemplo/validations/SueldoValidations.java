package org.ejemplo.validations;


import org.ejemplo.exceptions.SueldoException;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.modelos.dtos.UsuarioDTO;
import org.springframework.http.HttpStatus;
import org.ejemplo.modelos.Sueldo;
import org.ejemplo.repository.SueldoRepository;

import java.util.Optional;


public class SueldoValidations {
    SueldoRepository sueldoRepository;
    public void validateSueldoForCreate (Sueldo sueldo, Optional<UsuarioDTO> optionalUser,Boolean sueldoRepetido, UsuarioDTO administrador) throws SueldoException{

        if (sueldo.getUsuario() == null) {
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "Error en el campo Usuario", "El usuario no puede ser nulo");
        }
        if (sueldoRepetido) {
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "El usuario ya tiene sueldo", "Solo puede existir un sueldo por usuario");
        }
        if (optionalUser.isEmpty()){
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "El usuario no existe", "Necesita tener un usuario existente para crear un sueldo");
        }
        UsuarioDTO usuario = optionalUser.get();

        if (!administrador.getRole().equals("ADMINISTRADOR")) {
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "No tiene permisos para crear un sueldo", "Solo los administradores pueden crear sueldos");
        }
        if (usuario.getUser().equals(administrador.getUser())) {
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "No puede crear su propio sueldo", "Los usuarios no pueden crear su propio sueldo");
        }
        Double bono = sueldo.getBono();
        if (bono == null|| bono < 0 || bono > 1 ){
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "Error en el campo bono", "Los bonos deben representarse entre 0, 0,5 y 1");
        }
        if (sueldo.getRevisionPerformance() == null) {
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "Error en el campo Revisión de Performance", "La revisión de performance no puede ser nula");
        }

    }
    public static void validateSueldoForUpdate(Sueldo sueldo, Usuario usuario, Usuario administrador) throws SueldoException{

        if (!administrador.getRole().equals("ADMINISTRADOR")) {
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "No tiene permisos para actualizar el sueldo", "Solo los administradores pueden actualizar sueldos");
        }
        if (sueldo.getUsuario().getUser().equals(usuario.getUser())) {
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "No puede actualizar su propio sueldo", "Los usuarios no pueden actualizar su propio sueldo");
        }
        if (sueldo.getRevisionPerformance().equals("PENDIENTE") || sueldo.getRevisionPerformance().equals("COMPLETADO")) {
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "No se puede actualizar el sueldo", "La revisión de performance está en estado PENDIENTE o COMPLETADO");
        }
        Double bono = sueldo.getBono();
        if (bono == null|| bono <0 || bono > 1 ){
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "Error en el campo bono", "Los bonos deben representarse entre 0, 0,5 y 1");
        }
    }

    public void validarParaVerSueldo(Usuario usuario, Usuario administrador) throws SueldoException {
        if (!usuario.getRole().equals("ADMINISTRADOR") && !usuario.getRole().equals(administrador.getRole())) {
            throw new SueldoException(HttpStatus.PRECONDITION_FAILED, "No tiene permisos para ver el sueldo", "Solo puede ver su sueldo de no ser que sea administrador");
        }
    }
}
