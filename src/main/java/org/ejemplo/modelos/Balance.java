package org.ejemplo.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
@Entity
@Table
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
    private Date desde;
    private Date hasta;
    private String nombre;
}
