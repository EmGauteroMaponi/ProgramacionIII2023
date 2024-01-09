package org.ejemplo.modelos.dtos;

import lombok.Data;

@Data
public class RegistroDTO {
    private String id;
    private String accion;
    private UsuarioDTO user;
    private String detalles;
    private Object object;
}
