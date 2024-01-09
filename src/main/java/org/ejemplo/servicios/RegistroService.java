package org.ejemplo.servicios;

import com.google.gson.Gson;
import org.ejemplo.mapers.RegistroDtoMapper;
import org.ejemplo.modelos.Registro;
import org.ejemplo.modelos.dtos.RegistroDTO;
import org.ejemplo.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegistroService {
    @Autowired
    private RegistroRepository repository;
    @Autowired
    private RegistroDtoMapper mapper;

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

    public List<RegistroDTO> getAll(){
        return repository.findAll().stream().map(reg -> mapper.registroToRegistroDTO(reg)).collect(Collectors.toList());
    }
}
