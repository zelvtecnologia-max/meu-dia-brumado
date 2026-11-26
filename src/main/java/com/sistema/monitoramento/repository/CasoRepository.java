package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Caso;
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
public interface CasoRepository extends JpaRepository<Caso, Long> {

    Optional<Caso> findByNumeroCaso(String numeroCaso);

    List<Caso> findByVitima(Pessoa vitima);

    List<Caso> findByAgressorPrincipal(Pessoa agressor);

    List<Caso> findByResponsavel(Usuario responsavel);

    List<Caso> findByStatus(Caso.StatusCaso status);

    List<Caso> findByGrauRisco(Caso.GrauRisco grauRisco);

    Page<Caso> findByStatus(Caso.StatusCaso status, Pageable pageable);

    Page<Caso> findByVitima(Pessoa vitima, Pageable pageable);

    @Query("SELECT c FROM Caso c WHERE LOWER(c.numeroCaso) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(c.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Caso> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    List<Caso> findByDataAberturaBetween(LocalDate inicio, LocalDate fim);

    long countByStatus(Caso.StatusCaso status);

    boolean existsByNumeroCaso(String numeroCaso);
}