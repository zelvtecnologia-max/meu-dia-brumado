package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.UsuarioAdministrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioAdministrativoRepository extends JpaRepository<UsuarioAdministrativo, Integer> {

    Optional<UsuarioAdministrativo> findByEmail(String email);

    Optional<UsuarioAdministrativo> findByEmailAndAtivoTrue(String email);

    boolean existsByEmail(String email);
}