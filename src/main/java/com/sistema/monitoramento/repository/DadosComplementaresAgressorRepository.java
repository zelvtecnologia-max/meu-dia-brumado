package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.DadosComplementaresAgressor;
import com.sistema.monitoramento.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DadosComplementaresAgressorRepository extends JpaRepository<DadosComplementaresAgressor, Long> {

    Optional<DadosComplementaresAgressor> findByPessoa(Pessoa pessoa);

    boolean existsByPessoa(Pessoa pessoa);
}