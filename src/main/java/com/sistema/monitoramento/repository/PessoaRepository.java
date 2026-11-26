package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByCpf(String cpf);

    Optional<Pessoa> findByEmail(String email);

    List<Pessoa> findByTipoPessoa(Pessoa.TipoPessoa tipoPessoa);

    List<Pessoa> findByNomeCompletoContainingIgnoreCase(String nome);

    Page<Pessoa> findByTipoPessoa(Pessoa.TipoPessoa tipoPessoa, Pageable pageable);

    @Query("SELECT p FROM Pessoa p WHERE LOWER(p.nomeCompleto) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(p.cpf) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(p.email) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Pessoa> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    List<Pessoa> findByCidadeAndEstado(String cidade, String estado);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}