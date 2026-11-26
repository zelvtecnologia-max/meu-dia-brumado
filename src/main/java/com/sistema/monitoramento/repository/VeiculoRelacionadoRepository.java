package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Pessoa;
import com.sistema.monitoramento.model.VeiculoRelacionado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoRelacionadoRepository extends JpaRepository<VeiculoRelacionado, Long> {

    Optional<VeiculoRelacionado> findByPlaca(String placa);

    List<VeiculoRelacionado> findByPessoa(Pessoa pessoa);

    Page<VeiculoRelacionado> findByPessoa(Pessoa pessoa, Pageable pageable);

    List<VeiculoRelacionado> findByMarcaAndModelo(String marca, String modelo);

    boolean existsByPlaca(String placa);
}