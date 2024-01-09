package org.ejemplo.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "registros")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Registro {
    @Id
    private String id;
    private String accion;
    private String user;
    private String detalles;
    private String object;
}
