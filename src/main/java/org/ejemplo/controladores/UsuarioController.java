package org.ejemplo.controladores;

import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.UserException;
import org.ejemplo.modelos.Login;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.servicios.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UsuarioController {

    @Autowired
    public UsersService service;

    @PostMapping("/registry")
    public ResponseEntity<String> createUser(@RequestBody Usuario usuario){
        try{
            String respuesta = service.guardarUsuario(usuario);
            log.info("Usuario creado de forma correcta {}", usuario.getUser());
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (UserException e){
            log.warn("No se esta cumpliendo con las validaciones. Usuario a crear: {}", usuario);
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Usuario>> getAll(){
        return ResponseEntity.ok(service.retornarUsuarios());
    }
    @DeleteMapping("/remove/{user}")
    public ResponseEntity<String> remove(@PathVariable("user") String user){
        try {
            service.borrarUsuarios(user);
            return ResponseEntity.ok("Usuario Borrado Correctamente");
        } catch (UserException e){
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login login) {
        String respuesta = service.login(login);
        if (respuesta.contains("Error")){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }
}
