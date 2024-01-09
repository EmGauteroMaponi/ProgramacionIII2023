package org.ejemplo.servicios;

import com.google.gson.Gson;
import org.ejemplo.modelos.Registro;
import org.ejemplo.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RegistroService {
    @Autowired
    private RegistroRepository repository;

    public void registrar(String accion, String usuario, String detalle, Object object){
        Gson gson = new Gson();
        String json = object!=null ? gson.toJson(object):null;
        Registro registro = new Registro();
        registro.setId(UUID.randomUUID().toString());
        registro.setAccion(accion);
        registro.setUser(usuario);
        registro.setDetalles(detalle);
        registro.setObject(json);

        repository.save(registro);
    }

    public List<Registro> getAll(){
        return repository.findAll();
    }
}
