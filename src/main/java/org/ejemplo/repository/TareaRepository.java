package org.ejemplo.repository;

import org.ejemplo.modelos.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, String> {
}
