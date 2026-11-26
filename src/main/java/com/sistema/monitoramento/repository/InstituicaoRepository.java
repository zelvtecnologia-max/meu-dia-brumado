package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer> {

    List<Instituicao> findByAtivoTrue();

    List<Instituicao> findByTipoInstituicao(Instituicao.TipoInstituicao tipo);

    Optional<Instituicao> findByNome(String nome);

    List<Instituicao> findByNomeContainingIgnoreCase(String nome);
}