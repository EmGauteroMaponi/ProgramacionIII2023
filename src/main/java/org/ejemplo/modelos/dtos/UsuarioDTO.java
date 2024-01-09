package org.ejemplo.modelos.dtos;

import lombok.Data;
import org.ejemplo.modelos.Usuario;

@Data
public class UsuarioDTO {
    private String user;
    private String role;

    public UsuarioDTO(Usuario usuario){
        this.user =usuario.getUser();
        this.role = usuario.getRole();
    }
}
