package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {

    List<Administrador> findByPodeGerenciarUsuariosTrue();

    List<Administrador> findByNivelAcesso(String nivelAcesso);

    boolean existsByEmail(String email);
}