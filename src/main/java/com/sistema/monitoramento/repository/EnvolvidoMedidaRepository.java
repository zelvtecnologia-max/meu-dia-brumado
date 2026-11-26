package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.EnvolvidoMedida;
import com.sistema.monitoramento.model.MedidaProtetiva;
import com.sistema.monitoramento.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvolvidoMedidaRepository extends JpaRepository<EnvolvidoMedida, Long> {

    List<EnvolvidoMedida> findByMedidaProtetiva(MedidaProtetiva medidaProtetiva);

    List<EnvolvidoMedida> findByPessoa(Pessoa pessoa);

    List<EnvolvidoMedida> findByMedidaProtetivaAndPessoa(MedidaProtetiva medidaProtetiva, Pessoa pessoa);

    List<EnvolvidoMedida> findByTipoEnvolvimento(String tipoEnvolvimento);

    Page<EnvolvidoMedida> findByMedidaProtetiva(MedidaProtetiva medidaProtetiva, Pageable pageable);

    Page<EnvolvidoMedida> findByPessoa(Pessoa pessoa, Pageable pageable);

    boolean existsByMedidaProtetivaAndPessoa(MedidaProtetiva medidaProtetiva, Pessoa pessoa);
}