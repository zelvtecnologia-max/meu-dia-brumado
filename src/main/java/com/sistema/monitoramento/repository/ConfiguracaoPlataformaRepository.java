package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.ConfiguracaoPlataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfiguracaoPlataformaRepository extends JpaRepository<ConfiguracaoPlataforma, Integer> {

    Optional<ConfiguracaoPlataforma> findByNome(String nome);

    List<ConfiguracaoPlataforma> findByCategoria(String categoria);

    List<ConfiguracaoPlataforma> findByEditavelTrue();
}