package org.ejemplo.servicios;

import org.ejemplo.modelos.Autentication;
import org.ejemplo.modelos.dtos.LogDTO;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.repository.AutenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private AutenticationRepository repository;
    public LogDTO createToken(Usuario usuario){
        if (repository.existsById(usuario.getUser())){
            repository.deleteById(usuario.getUser());
        }
        Autentication autentication = new Autentication();
        autentication.setUser(usuario.getUser());
        autentication.setToken(usuario.getUser() + UUID.randomUUID());
        autentication.setVencimiento(getVencimiento());
        repository.save(autentication);
        return new LogDTO(usuario.getUser(), usuario.getRole(), autentication.getToken());
    }

    public Autentication validarToken(String token) throws AuthenticationException {
        Optional<Autentication> optionalAutentication = repository.findByToken(token);
        if (optionalAutentication.isEmpty()){
            throw new AuthenticationException("El token no existe");
        }
        Autentication autentication = optionalAutentication.get();
        if (autentication.getVencimiento().before(new Date())){
            throw new AuthenticationException("El token está vencido");
        }
        autentication.setVencimiento(getVencimiento());
        repository.save(autentication);
        return autentication;
    }

    public void eliminarToken(String token) throws AuthenticationException {
        Optional<Autentication> optionalAutentication = repository.findByToken(token);
        if (optionalAutentication.isEmpty()){
            throw new AuthenticationException("El token no existe");
        }
        Autentication autentication = optionalAutentication.get();
        repository.deleteById(autentication.getUser());
    }



    private Date getVencimiento(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MINUTE, 15);
        return c.getTime();
    }
}
