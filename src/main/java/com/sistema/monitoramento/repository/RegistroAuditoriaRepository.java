package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.RegistroAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistroAuditoriaRepository extends JpaRepository<RegistroAuditoria, Integer> {

    List<RegistroAuditoria> findByIdUsuarioAndTipoUsuario(Integer idUsuario, String tipoUsuario);

    List<RegistroAuditoria> findByEntidadeAfetada(String entidadeAfetada);

    List<RegistroAuditoria> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    List<RegistroAuditoria> findByAcao(String acao);

    List<RegistroAuditoria> findBySucessoFalse();
}