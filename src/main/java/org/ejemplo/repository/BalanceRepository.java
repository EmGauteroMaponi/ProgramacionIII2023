package org.ejemplo.repository;

import org.ejemplo.modelos.Autentication;
import org.ejemplo.modelos.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {

}
