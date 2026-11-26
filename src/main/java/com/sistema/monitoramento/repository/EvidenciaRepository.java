package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.Evidencia;
import com.sistema.monitoramento.model.Ocorrencia;
import com.sistema.monitoramento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvidenciaRepository extends JpaRepository<Evidencia, Long> {

    Optional<Evidencia> findByCodigoEvidencia(String codigoEvidencia);

    List<Evidencia> findByOcorrencia(Ocorrencia ocorrencia);

    List<Evidencia> findByCaso(Caso caso);

    List<Evidencia> findByUsuarioColetor(Usuario usuarioColetor);

    List<Evidencia> findByStatus(Evidencia.StatusEvidencia status);

    List<Evidencia> findByTipoEvidencia(String tipoEvidencia);

    Page<Evidencia> findByCaso(Caso caso, Pageable pageable);

    Page<Evidencia> findByStatus(Evidencia.StatusEvidencia status, Pageable pageable);

    boolean existsByCodigoEvidencia(String codigoEvidencia);
}