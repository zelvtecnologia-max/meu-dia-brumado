package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.MedidaProtetiva;
import com.sistema.monitoramento.model.Pessoa;
import com.sistema.monitoramento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedidaProtetivaRepository extends JpaRepository<MedidaProtetiva, Long> {

    Optional<MedidaProtetiva> findByNumeroProcesso(String numeroProcesso);

    Optional<MedidaProtetiva> findByNumeroMedida(String numeroMedida);

    List<MedidaProtetiva> findByCaso(Caso caso);

    List<MedidaProtetiva> findByVitima(Pessoa vitima);

    List<MedidaProtetiva> findByAgressor(Pessoa agressor);

    List<MedidaProtetiva> findByUsuarioConcedente(Usuario usuarioConcedente);

    List<MedidaProtetiva> findByStatus(MedidaProtetiva.StatusMedida status);

    List<MedidaProtetiva> findByTipoUrgencia(MedidaProtetiva.TipoUrgencia tipoUrgencia);

    Page<MedidaProtetiva> findByStatus(MedidaProtetiva.StatusMedida status, Pageable pageable);

    Page<MedidaProtetiva> findByVitima(Pessoa vitima, Pageable pageable);

    @Query("SELECT m FROM MedidaProtetiva m WHERE m.dataVencimento BETWEEN :inicio AND :fim AND m.status IN (:statuses)")
    List<MedidaProtetiva> findVencendoEntre(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim, @Param("statuses") List<MedidaProtetiva.StatusMedida> statuses);

    @Query("SELECT m FROM MedidaProtetiva m WHERE m.dataVencimento < :hoje AND m.status IN (:statuses)")
    List<MedidaProtetiva> findVencidas(@Param("hoje") LocalDate hoje, @Param("statuses") List<MedidaProtetiva.StatusMedida> statuses);

    long countByStatus(MedidaProtetiva.StatusMedida status);

    boolean existsByNumeroProcesso(String numeroProcesso);
}