package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.ConteudoEducativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConteudoEducativoRepository extends JpaRepository<ConteudoEducativo, Integer> {

    List<ConteudoEducativo> findByStatus(ConteudoEducativo.StatusConteudo status);

    List<ConteudoEducativo> findByCategoria(String categoria);

    List<ConteudoEducativo> findByTituloContainingIgnoreCase(String titulo);

    List<ConteudoEducativo> findByStatusAndCategoria(ConteudoEducativo.StatusConteudo status, String categoria);
}