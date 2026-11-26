package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.CanalOficial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanalOficialRepository extends JpaRepository<CanalOficial, Integer> {

    List<CanalOficial> findByAtivoTrue();

    List<CanalOficial> findByFinalidadeContainingIgnoreCase(String finalidade);
}