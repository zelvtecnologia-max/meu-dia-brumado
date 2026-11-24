package com.meudia.api.domain.repository;

import com.meudia.api.domain.model.DiaRegistro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaRegistroRepository extends JpaRepository<DiaRegistro, Long> {

    Page<DiaRegistro> findByStatus(DiaRegistro.StatusRegistro status, Pageable pageable);

    List<DiaRegistro> findByDataRegistro(LocalDateTime dataRegistro);

    @Query("SELECT r FROM DiaRegistro r WHERE r.dataRegistro BETWEEN :inicio AND :fim")
    Page<DiaRegistro> findByDataRegistroBetween(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable
    );

    @Query("SELECT r FROM DiaRegistro r WHERE r.dataRegistro BETWEEN :inicio AND :fim")
    List<DiaRegistro> findByDataRegistroBetween(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    boolean existsById(Long id);
}