package org.ejemplo.servicios;

import org.ejemplo.exceptions.BalanceException;
import org.ejemplo.modelos.Tarea;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.repository.TareaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TareaService {
    private TareaRepository tareaRepository;
    private UsersService usersService;

    TareaService(UsersService usersService, TareaRepository tareaRepository){
        this.usersService = usersService;
        this.tareaRepository = tareaRepository;
    }


    public Tarea crearTarea(String user, Tarea tarea) throws BalanceException {
        validateTareaParaCrear(user, tarea);
        tarea.setFechaCreacion(new Date());
        Usuario usuario = usersService.findByUser(user).get();
        tarea.setAutor(usuario);
        tarea.setId(user + UUID.randomUUID());
        return tareaRepository.save(tarea);
    }

    public Tarea actualizarTarea(String user, Tarea tarea) throws BalanceException {
        if (tarea.getId() == null || tarea.getId().isBlank()){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede actualizar la tarea", "Debe especificar el Id de la tarea que desea actualizar");
        }
        if (!tareaRepository.existsById(tarea.getId())){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede actualizar la tarea", "El id de la tarea a actualizar no se encuentra registrado en nuestro sistema");
        }
        if(usersService.findByUser(user).isEmpty()){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede actualizar la tarea", "El usuario no existe");
        }
        Usuario usuario = usersService.findByUser(user).get();
        if (tarea.getNombre() == null || tarea.getNombre().isBlank() ){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede actualizar la tarea","Debe contener el nombre de la tarea");
        }
        if (tarea.getEncargado() == null && !usuario.getRole().equalsIgnoreCase("administrador")) {
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede actualizar la tarea", "Para modificar la tarea debe ingresar el usuario encargado" );
        }
        if (tarea.getEncargado() != null){
            if (tarea.getEncargado().getUser() == null || tarea.getEncargado().getUser().isBlank()){
                throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede actualizar la tarea", "Para modificar la tarea debe especificar el nombre de usuario del encargado" );
            }
            Optional<Usuario> encargadoOptional = usersService.findByUser(tarea.getEncargado().getUser());
            if (encargadoOptional.isEmpty()){
                throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede actualizar la tarea", "El encargado no se encuentra registrado en nuestro sistema");
            }

        }
        Tarea tareaRegistrada = tareaRepository.findById(tarea.getId()).get();
        tareaRegistrada.setNombre(tarea.getNombre());
        tareaRegistrada.setDescripcion(tarea.getDescripcion());
        if (usuario.getRole().equalsIgnoreCase("administrador") || tarea.getEncargado() != null){
            tareaRegistrada.setEncargado(tarea.getEncargado());
        }
        return tareaRepository.save(tareaRegistrada);
    }

    public Tarea cerrarTarea(String user, String id) throws BalanceException {
        if (id == null || id.isBlank()){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede finalizar la tarea", "Debe especificar el Id de la tarea que desea finalizar");
        }
        if (!tareaRepository.existsById(id)){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede finalizar la tarea", "El id de la tarea a finalizar no se encuentra registrado en nuestro sistema");
        }
        Tarea tareaRegistrada = tareaRepository.findById(id).get();
        if(usersService.findByUser(user).isEmpty()){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede finalizar la tarea", "El usuario no existe");
        }
        if (tareaRegistrada.getNombre() == null || tareaRegistrada.getNombre().isBlank() ){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede finalizar la tarea","Debe contener el nombre de la tarea");
        }
        if (tareaRegistrada.getEncargado() == null) {
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede finalizar la tarea", "Para finalizar la tarea debe ingresar el usuario encargado" );
        }
        if (tareaRegistrada.getEncargado().getUser() == null ||tareaRegistrada.getEncargado().getUser().isBlank()){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede finalizar la tarea", "Para finalizar la tarea debe especificar el nombre de usuario del encargado" );
        }
        Optional<Usuario> encargadoOptional = usersService.findByUser(tareaRegistrada.getEncargado().getUser());
        if (encargadoOptional.isEmpty()){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede finalizar la tarea", "El encargado no se encuentra registrado en nuestro sistema");
        }
        if (tareaRegistrada.getFechaRealizado() != null){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede finalizar la tarea", "La tarea ya se encuentra finalizada");
        }
        tareaRegistrada.setFechaRealizado(new Date());
        return tareaRepository.save(tareaRegistrada);

    }

    public List<Tarea> obtenerTodos(){
        return tareaRepository.findAll();
    }

    public List<Tarea> findByEncargado(String user){
        return tareaRepository.findAll().stream().filter( t -> t.getEncargado() != null && t.getEncargado().getUser().equalsIgnoreCase(user)).collect(Collectors.toList());
    }

    public List<Tarea> findByAutor(String user){
        return tareaRepository.findAll().stream().filter( t -> t.getAutor() != null && t.getAutor().getUser().equalsIgnoreCase(user)).collect(Collectors.toList());
    }

    private void validateTareaParaCrear(String user, Tarea tarea) throws BalanceException {
        if(usersService.findByUser(user).isEmpty()){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede cargar la tarea","El usuario no existe");
        }
        if (!usersService.findByUser(user).get().getRole().equalsIgnoreCase("administrador")){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede cargar la tarea","Solo un administrador puede crear la tarea");
        }
        if (tarea.getNombre() == null || tarea.getNombre().isBlank() ){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede cargar la tarea","Debe contener el nombre de la tarea");
        }
        if (tarea.getEncargado() != null){
            Usuario encargado = tarea.getEncargado();
            if (encargado.getUser() == null){
                throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede cargar la tarea", "Debe especificar el nombre de usuario del encargado" );
            }
            Optional<Usuario> encargadoOptional = usersService.findByUser(encargado.getUser());
            if (encargadoOptional.isEmpty()){
                throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede cargar la tarea", "El encargado no se encuentra registrado en nuestro sistema");
            }
        }
        if (tarea.getFechaRealizado() != null){
            throw new BalanceException(HttpStatus.PRECONDITION_FAILED, "No se puede cargar la tarea", "No se puede cargar la tarea con una fecha de realizado");
        }
    }
}
