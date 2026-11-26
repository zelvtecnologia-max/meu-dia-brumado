package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Usuaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariaRepository extends JpaRepository<Usuaria, Integer> {

    Optional<Usuaria> findByEmail(String email);

    Optional<Usuaria> findByEmailAndStatus(String email, Usuaria.StatusUsuaria status);

    List<Usuaria> findByStatus(Usuaria.StatusUsuaria status);

    @Query("SELECT u FROM Usuaria u WHERE u.nomeCompleto LIKE %:termo% OR u.email LIKE %:termo%")
    List<Usuaria> buscarPorTermo(@Param("termo") String termo);

    boolean existsByEmail(String email);
}