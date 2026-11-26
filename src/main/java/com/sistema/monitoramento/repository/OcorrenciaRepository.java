package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.MedidaProtetiva;
import com.sistema.monitoramento.model.Ocorrencia;
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
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    Optional<Ocorrencia> findByNumeroOcorrencia(String numeroOcorrencia);

    List<Ocorrencia> findByCaso(Caso caso);

    List<Ocorrencia> findByMedidaProtetiva(MedidaProtetiva medidaProtetiva);

    List<Ocorrencia> findByVitima(Pessoa vitima);

    List<Ocorrencia> findByAgressor(Pessoa agressor);

    List<Ocorrencia> findByUsuarioRegistro(Usuario usuarioRegistro);

    List<Ocorrencia> findByStatus(Ocorrencia.StatusOcorrencia status);

    List<Ocorrencia> findByGravidade(Ocorrencia.Gravidade gravidade);

    List<Ocorrencia> findByDataOcorrencia(LocalDate dataOcorrencia);

    List<Ocorrencia> findByDataOcorrenciaBetween(LocalDate inicio, LocalDate fim);

    Page<Ocorrencia> findByCaso(Caso caso, Pageable pageable);

    Page<Ocorrencia> findByStatus(Ocorrencia.StatusOcorrencia status, Pageable pageable);

    Page<Ocorrencia> findByDataOcorrenciaBetween(LocalDate inicio, LocalDate fim, Pageable pageable);

    @Query("SELECT o FROM Ocorrencia o WHERE LOWER(o.numeroOcorrencia) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(o.descricao) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(o.localOcorrencia) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Ocorrencia> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    long countByStatus(Ocorrencia.StatusOcorrencia status);

    boolean existsByNumeroOcorrencia(String numeroOcorrencia);
}