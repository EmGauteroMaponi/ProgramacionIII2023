package org.ejemplo.servicios;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.UserException;
import org.ejemplo.exceptions.ValidationException;
import org.ejemplo.modelos.dtos.LogDTO;
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
    AuthenticationService authenticationService;

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

    public Optional<Usuario> findByUser(String user){
        return usuarioRepository.findById(user);
    }

    public LogDTO login(Login login) throws UserException {
        if (usuarioRepository.findAll().stream().anyMatch(usuario -> usuario.getUser().equals(login.getUser()))){
            Usuario usuario = usuarioRepository.findById(login.getUser()).get();
            if (usuario.getPassword().equals(login.getPassword())){
                return authenticationService.createToken(usuario);
            }
        }
        throw new UserException(HttpStatus.UNAUTHORIZED,"Login fallido", "Tus datos de inicio de session son invalidos");
    }
}
