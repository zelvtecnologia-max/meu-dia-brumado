package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.RelatorioEstatistico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RelatorioEstatisticoRepository extends JpaRepository<RelatorioEstatistico, Integer> {

    List<RelatorioEstatistico> findByPeriodo(String periodo);

    List<RelatorioEstatistico> findByDataGeracaoBetween(LocalDate inicio, LocalDate fim);

    List<RelatorioEstatistico> findByGeradoPorIdUsuario(Integer idUsuario);

    List<RelatorioEstatistico> findByStatus(RelatorioEstatistico.StatusRelatorio status);
}