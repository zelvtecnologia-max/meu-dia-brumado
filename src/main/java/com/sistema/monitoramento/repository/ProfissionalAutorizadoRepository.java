package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.ProfissionalAutorizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalAutorizadoRepository extends JpaRepository<ProfissionalAutorizado, Integer> {

    List<ProfissionalAutorizado> findByOrgaoLotacao(String orgaoLotacao);

    List<ProfissionalAutorizado> findByEspecialidade(String especialidade);

    Optional<ProfissionalAutorizado> findByRegistroProfissional(String registroProfissional);
}