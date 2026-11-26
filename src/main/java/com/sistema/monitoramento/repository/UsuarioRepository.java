package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByMatricula(String matricula);

    List<Usuario> findByPerfil(Usuario.PerfilUsuario perfil);

    List<Usuario> findByStatus(Usuario.StatusUsuario status);

    Page<Usuario> findByStatus(Usuario.StatusUsuario status, Pageable pageable);

    List<Usuario> findByOrgaoLotacao(String orgaoLotacao);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByMatricula(String matricula);
}