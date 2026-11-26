package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Integer> {

    Optional<Permissao> findByNome(String nome);

    List<Permissao> findByRecurso(String recurso);

    List<Permissao> findByRecursoAndAcao(String recurso, String acao);
}