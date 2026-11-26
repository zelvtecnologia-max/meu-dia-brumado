package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.ParticipacaoCaso;
import com.sistema.monitoramento.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipacaoCasoRepository extends JpaRepository<ParticipacaoCaso, Long> {

    List<ParticipacaoCaso> findByCaso(Caso caso);

    List<ParticipacaoCaso> findByPessoa(Pessoa pessoa);

    List<ParticipacaoCaso> findByCasoAndPessoa(Caso caso, Pessoa pessoa);

    List<ParticipacaoCaso> findByTipoParticipacao(String tipoParticipacao);

    Page<ParticipacaoCaso> findByCaso(Caso caso, Pageable pageable);

    Page<ParticipacaoCaso> findByPessoa(Pessoa pessoa, Pageable pageable);

    boolean existsByCasoAndPessoa(Caso caso, Pessoa pessoa);
}