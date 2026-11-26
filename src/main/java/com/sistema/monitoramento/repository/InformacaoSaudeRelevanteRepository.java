package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.InformacaoSaudeRelevante;
import com.sistema.monitoramento.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformacaoSaudeRelevanteRepository extends JpaRepository<InformacaoSaudeRelevante, Long> {

    List<InformacaoSaudeRelevante> findByPessoa(Pessoa pessoa);

    List<InformacaoSaudeRelevante> findByPessoaAndTipoInformacao(Pessoa pessoa, String tipoInformacao);

    Page<InformacaoSaudeRelevante> findByPessoa(Pessoa pessoa, Pageable pageable);
}