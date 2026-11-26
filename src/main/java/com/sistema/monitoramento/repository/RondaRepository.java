package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.MedidaProtetiva;
import com.sistema.monitoramento.model.Ronda;
import com.sistema.monitoramento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RondaRepository extends JpaRepository<Ronda, Long> {

    Optional<Ronda> findByCodigoRonda(String codigoRonda);

    List<Ronda> findByMedidaProtetiva(MedidaProtetiva medidaProtetiva);

    List<Ronda> findByUsuarioResponsavel(Usuario usuarioResponsavel);

    List<Ronda> findByUsuarioExecutor(Usuario usuarioExecutor);

    List<Ronda> findByStatus(Ronda.StatusRonda status);

    List<Ronda> findByTipoRonda(Ronda.TipoRonda tipoRonda);

    List<Ronda> findByDataRonda(LocalDate dataRonda);

    List<Ronda> findByDataRondaBetween(LocalDate inicio, LocalDate fim);

    Page<Ronda> findByStatus(Ronda.StatusRonda status, Pageable pageable);

    Page<Ronda> findByDataRondaBetween(LocalDate inicio, LocalDate fim, Pageable pageable);

    Page<Ronda> findByUsuarioExecutor(Usuario usuarioExecutor, Pageable pageable);

    @Query("SELECT r FROM Ronda r WHERE r.dataRonda = :hoje AND r.status IN (:statuses)")
    List<Ronda> findAgendadasParaHoje(@Param("hoje") LocalDate hoje, @Param("statuses") List<Ronda.StatusRonda> statuses);

    long countByStatus(Ronda.StatusRonda status);

    boolean existsByCodigoRonda(String codigoRonda);
}