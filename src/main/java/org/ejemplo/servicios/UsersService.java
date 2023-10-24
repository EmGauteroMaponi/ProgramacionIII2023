package org.ejemplo.servicios;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.UserException;
import org.ejemplo.exceptions.ValidationException;
import org.ejemplo.modelos.Log;
import org.ejemplo.modelos.Login;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.repository.UsuarioRepository;
import org.ejemplo.validations.UserValidations;
import org.ejemplo.validations.ValidationsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsersService {
    private static ValidationsInterface<Usuario, String, Object> validations = new UserValidations();
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    AutenticationService autenticationService;

    public String guardarUsuario(Usuario usuario) throws ValidationException {
        validations.validateToCreate(retornarUsuarios(), usuario, null);
        usuarioRepository.save(usuario);
        return "usuario cargado correctamente";
    }

    public List<Usuario> retornarUsuarios(){
        return usuarioRepository.findAll();
    }

    public void borrarUsuarios(String user) throws ValidationException {
        validations.validateToDelete(retornarUsuarios(),user);
        usuarioRepository.deleteById(user);
    }

    public Log login(Login login) throws UserException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(login.getUser());
        if (optionalUsuario.isPresent()){
            Usuario usuario = optionalUsuario.get();
            if (usuario.getPassword().equals(login.getPassword())){
                return autenticationService.createToken(usuario);
            }
        }
        throw new UserException(HttpStatus.UNAUTHORIZED,"Login fallido", "Tus datos de inicio de session son invalidos");
    }
}
