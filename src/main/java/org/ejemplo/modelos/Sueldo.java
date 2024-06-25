package org.ejemplo.modelos;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ejemplo.modelos.enums.RevisionPerformance;

import java.util.Date;

@Entity
@Table
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Sueldo {
    @Id
    private String id;

    @OneToOne(fetch = FetchType.EAGER)
    @Nullable
    private Usuario usuario;

    @OneToOne(fetch = FetchType.EAGER)
    private Usuario administrador;

    @Column
    private Double sueldo;
    @Column
    private Date fechaDeActualizacion;
    @Column
    private Double bono;

    @Enumerated(EnumType.STRING)
    @Column
    private RevisionPerformance revisionPerformance;

}
