package org.ejemplo.modelos;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ejemplo.converters.JpaConverterJson;

import java.util.Map;

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
    @Convert(converter = JpaConverterJson.class)
    private Map objects;
}
