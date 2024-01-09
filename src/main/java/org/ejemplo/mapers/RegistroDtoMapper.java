package org.ejemplo.mapers;

import com.google.gson.Gson;
import org.ejemplo.modelos.Registro;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.modelos.dtos.RegistroDTO;
import org.ejemplo.modelos.dtos.UsuarioDTO;
import org.ejemplo.servicios.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroDtoMapper {
    @Autowired
    private UsersService usersService;

    public RegistroDTO registroToRegistroDTO(Registro registro){
        RegistroDTO registroDTO = new RegistroDTO();
        registroDTO.setAccion(registro.getAccion());
        registroDTO.setDetalles(registro.getDetalles());
        registroDTO.setId(registro.getId());
        registroDTO.setUser(usersService.getByUser(registro.getUser()).orElse(new UsuarioDTO(new Usuario(registro.getUser(),"", "System"))));
        registroDTO.setObject(JsonToObject(registro.getObject()));
        return registroDTO;
    }
    public Object JsonToObject(String json){
        return new Gson().fromJson(json,Object.class);
    }
}
