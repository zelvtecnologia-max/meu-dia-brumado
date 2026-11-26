package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.PerfilApoio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilApoioRepository extends JpaRepository<PerfilApoio, Integer> {

    Optional<PerfilApoio> findByUsuariaIdUsuaria(Integer idUsuaria);
}