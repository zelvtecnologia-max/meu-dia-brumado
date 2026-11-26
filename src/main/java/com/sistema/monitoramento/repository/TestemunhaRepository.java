package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.Ocorrencia;
import com.sistema.monitoramento.model.Pessoa;
import com.sistema.monitoramento.model.Testemunha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestemunhaRepository extends JpaRepository<Testemunha, Long> {

    List<Testemunha> findByPessoa(Pessoa pessoa);

    List<Testemunha> findByOcorrencia(Ocorrencia ocorrencia);

    List<Testemunha> findByCaso(Caso caso);

    Page<Testemunha> findByCaso(Caso caso, Pageable pageable);

    Page<Testemunha> findByPessoa(Pessoa pessoa, Pageable pageable);
}