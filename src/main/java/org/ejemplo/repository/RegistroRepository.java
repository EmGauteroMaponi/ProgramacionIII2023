package org.ejemplo.repository;

import org.ejemplo.modelos.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, String> {
}