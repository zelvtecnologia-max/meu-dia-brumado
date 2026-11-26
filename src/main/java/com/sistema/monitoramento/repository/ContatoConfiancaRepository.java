package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.ContatoConfianca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoConfiancaRepository extends JpaRepository<ContatoConfianca, Integer> {

    List<ContatoConfianca> findByUsuariaIdUsuaria(Integer idUsuaria);

    List<ContatoConfianca> findByUsuariaIdUsuariaAndAtivoTrue(Integer idUsuaria);

    List<ContatoConfianca> findByUsuariaIdUsuariaAndContatoPrincipalTrue(Integer idUsuaria);
}