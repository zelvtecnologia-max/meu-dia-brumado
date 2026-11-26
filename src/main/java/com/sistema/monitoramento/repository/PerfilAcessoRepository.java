package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.PerfilAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerfilAcessoRepository extends JpaRepository<PerfilAcesso, Integer> {

    Optional<PerfilAcesso> findByNome(String nome);

    List<PerfilAcesso> findByAtivoTrue();
}