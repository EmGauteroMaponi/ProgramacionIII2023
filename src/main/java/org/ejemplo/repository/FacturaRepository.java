package org.ejemplo.repository;

import org.ejemplo.modelos.Factura;
import org.ejemplo.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, String> {
    List<Factura> findByVendedor_user(String vendedor);
    List<Factura> findByVendedor_role(String findByVendedor);
    List<Factura> findByPrecioGreaterThan(Double precio);
}
