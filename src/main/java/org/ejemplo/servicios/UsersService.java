package org.ejemplo.servicios;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.UserException;
import org.ejemplo.modelos.Login;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.repository.UserRepository;
import org.ejemplo.validations.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsersService {
    @Autowired //Utilizamos Auytowired para hacer inyeccion de dependencias por medio de springboot
    UserRepository userRepository;//Objeto para realizar consultas sobre la tabla usuario

    public String guardarUsuario(Usuario usuario) throws UserException {
        UserValidations.validateUserForRegister(retornarUsuarios(), usuario);
        userRepository.guardarUsuario(usuario);
        return "usuario cargado correctamente";
    }

    public List<Usuario> retornarUsuarios(){
        return userRepository.getAll();
    }

    public void borrarUsuarios(String user) throws Exception {
        if (!UserValidations.validateExistUser(retornarUsuarios(), user)){
            throw new UserException(HttpStatus.PRECONDITION_FAILED, "No se puede eliminar el usuario", String.format("El usuario %s no está registrado", user));
        }
        userRepository.removeUser(user);
    }

    public String login(Login login){
        Usuario usuario = getUser(login.getUser());
        if (usuario != null){
            if (usuario.getPassword().equals(login.getPassword())){
                return usuario.getRole();
            }
            else {
                return "Error, contraseña incorrecta";
            }
        }
        return String.format("Error, el usuario %s no existe, por favor registrese", login.getUser());
    }


    private Usuario getUser(String userName){
        for (Usuario usuario: retornarUsuarios()){
            if (usuario.getUser().equals(userName)){
                return usuario;
            }
        }
        return null;
    }
}
