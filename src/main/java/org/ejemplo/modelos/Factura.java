package org.ejemplo.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nro1;
    private String nro2;
    @OneToOne(fetch = FetchType.EAGER)
    private Cliente comprador;
    @OneToOne(fetch = FetchType.EAGER)
    private Usuario vendedor;
    private Date fecha;

    @OneToMany
    @JoinTable(
                name="factura_detalle",
                joinColumns={ @JoinColumn(name="factura", referencedColumnName="id") },
                inverseJoinColumns={ @JoinColumn(name="detalleFactura", referencedColumnName="id", unique=true) }
            )
    private List<DetalleFactura> detalles;
}
