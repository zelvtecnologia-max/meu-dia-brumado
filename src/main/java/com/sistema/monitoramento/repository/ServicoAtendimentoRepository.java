package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.ServicoAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoAtendimentoRepository extends JpaRepository<ServicoAtendimento, Integer> {

    List<ServicoAtendimento> findByInstituicaoIdInstituicao(Integer idInstituicao);

    List<ServicoAtendimento> findByAtivoTrue();

    List<ServicoAtendimento> findByInstituicaoIdInstituicaoAndAtivoTrue(Integer idInstituicao);
}