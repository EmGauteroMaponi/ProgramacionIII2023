package org.ejemplo.modelos.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogDTO {
    String rol;
    String token;
}
