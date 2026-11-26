package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.LogAuditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {

    List<LogAuditoria> findByUsuarioId(Long usuarioId);

    List<LogAuditoria> findByEntidadeAndEntidadeId(String entidade, Long entidadeId);

    List<LogAuditoria> findByAcao(String acao);

    List<LogAuditoria> findByCreatedAtBetween(LocalDateTime inicio, LocalDateTime fim);

    Page<LogAuditoria> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<LogAuditoria> findByEntidadeAndEntidadeId(String entidade, Long entidadeId, Pageable pageable);

    Page<LogAuditoria> findByCreatedAtBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
}