package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.RelatoAgressaoAnterior;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatoAgressaoAnteriorRepository extends JpaRepository<RelatoAgressaoAnterior, Long> {

    List<RelatoAgressaoAnterior> findByCaso(Caso caso);

    Page<RelatoAgressaoAnterior> findByCaso(Caso caso, Pageable pageable);

    List<RelatoAgressaoAnterior> findByDataOcorrenciaBetween(java.time.LocalDate inicio, java.time.LocalDate fim);
}