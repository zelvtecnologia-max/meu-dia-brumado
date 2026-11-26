package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.FuncaoPermitida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncaoPermitidaRepository extends JpaRepository<FuncaoPermitida, Integer> {

    List<FuncaoPermitida> findByModulo(String modulo);

    List<FuncaoPermitida> findByAtivoTrue();
}