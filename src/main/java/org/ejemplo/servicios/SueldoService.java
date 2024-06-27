package org.ejemplo.servicios;

import org.ejemplo.controladores.SueldoController;
import org.ejemplo.exceptions.ProductoException;
import org.ejemplo.exceptions.SueldoException;
import org.ejemplo.modelos.Producto;
import org.ejemplo.modelos.Sueldo;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.modelos.dtos.UsuarioDTO;
import org.ejemplo.repository.SueldoRepository;
import org.ejemplo.repository.UsuarioRepository;
import org.ejemplo.validations.ProductoValidations;
import org.ejemplo.validations.SueldoValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SueldoService {
    @Autowired
    UsersService usersService;
    @Autowired
    SueldoRepository sueldoRepository;

    SueldoValidations sueldoValidations = new SueldoValidations();

    public Sueldo crearSueldo(Sueldo sueldo, String userAdmin) throws SueldoException {
        UsuarioDTO administrador = usersService.getByUser(userAdmin).get();
        Optional<UsuarioDTO> usuario = usersService.getByUser(sueldo.getUsuario().getUser());
        Boolean sueldoRepetido = sueldoRepository.existsByUser(sueldo.getUsuario());
        sueldoValidations.validateSueldoForCreate(sueldo, usuario, sueldoRepetido, administrador);
        sueldo.setRevisionPerformance(Sueldo.RevisionPerformance.valueOf("COMPLETADO"));
        sueldo.setFechaDeActualizacion(new Date());
        sueldo.setId(sueldo.getUsuario().getUser());
        return sueldoRepository.save(sueldo);
    }

    public String validateSueldoForUpdate(Sueldo sueldo) throws SueldoException {
        SueldoValidations.validateSueldoForUpdate(sueldoRepository.findAll(), sueldo);
        return
    }

    public Optional<Usuario> findByUser(String user){
        return sueldoRepository.findById(user);
    }

}
