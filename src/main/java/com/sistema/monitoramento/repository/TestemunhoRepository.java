package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Testemunho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestemunhoRepository extends JpaRepository<Testemunho, Integer> {

    List<Testemunho> findByStatus(Testemunho.StatusTestemunho status);

    List<Testemunho> findByAutorAnonimoTrue();

    List<Testemunho> findByStatusOrderByDataPublicacaoDesc(Testemunho.StatusTestemunho status);
}