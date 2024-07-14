package org.ejemplo.repository;

import org.ejemplo.modelos.Sueldo;
import org.ejemplo.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SueldoRepository extends JpaRepository<Sueldo, Integer> {

    boolean existsByUser(Usuario usuario);
}