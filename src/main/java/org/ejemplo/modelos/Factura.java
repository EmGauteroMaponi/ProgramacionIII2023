package org.ejemplo.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Factura {
    @Id
    private String id;

    @Column(name="comprador")
    private String comprador;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "vendedor", foreignKey = @ForeignKey(name = "fk_factura_usuario"), nullable = false)
    private Usuario vendedor;

    @Column(name = "precio")
    private Double precio;
}
