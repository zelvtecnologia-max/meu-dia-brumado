package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.DependenteRelacionado;
import com.sistema.monitoramento.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DependenteRelacionadoRepository extends JpaRepository<DependenteRelacionado, Long> {

    List<DependenteRelacionado> findByPessoaResponsavel(Pessoa pessoaResponsavel);

    Page<DependenteRelacionado> findByPessoaResponsavel(Pessoa pessoaResponsavel, Pageable pageable);

    Optional<DependenteRelacionado> findByCpf(String cpf);

    List<DependenteRelacionado> findByGrauParentesco(String grauParentesco);

    boolean existsByCpf(String cpf);
}