package org.ejemplo.servicios;

import org.ejemplo.modelos.Registro;
import org.ejemplo.repository.RegistroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RegistroService {
    private RegistroRepository repository;

    public void registrar(String accion, String usuario, String detalle, Map object){
        Registro registro = new Registro();
        registro.setId(UUID.randomUUID().toString());
        registro.setAccion(accion);
        registro.setUser(usuario);
        registro.setDetalles(detalle);
        registro.setObjects(object);

        repository.save(registro);
    }

    public List<Registro> getAll(){
        return repository.findAll();
    }
}
