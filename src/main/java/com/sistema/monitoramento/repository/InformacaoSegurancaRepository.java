package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.InformacaoSeguranca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformacaoSegurancaRepository extends JpaRepository<InformacaoSeguranca, Integer> {

    List<InformacaoSeguranca> findByUsuariaIdUsuaria(Integer idUsuaria);

    List<InformacaoSeguranca> findByUsuariaIdUsuariaAndAtivoTrue(Integer idUsuaria);

    List<InformacaoSeguranca> findByCategoria(InformacaoSeguranca.CategoriaInformacao categoria);
}