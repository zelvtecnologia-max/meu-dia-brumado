package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.FatorRisco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FatorRiscoRepository extends JpaRepository<FatorRisco, Long> {

    List<FatorRisco> findByCaso(Caso caso);

    List<FatorRisco> findByCasoAndAtivoTrue(Caso caso);

    List<FatorRisco> findByCategoria(FatorRisco.CategoriaFator categoria);

    List<FatorRisco> findByNivel(FatorRisco.NivelRisco nivel);

    List<FatorRisco> findByCodigoFator(String codigoFator);

    Page<FatorRisco> findByCaso(Caso caso, Pageable pageable);

    long countByCasoAndAtivoTrue(Caso caso);
}