package com.sistema.monitoramento.repository;

import com.sistema.monitoramento.model.Alerta;
import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.MedidaProtetiva;
import com.sistema.monitoramento.model.Ocorrencia;
import com.sistema.monitoramento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    Optional<Alerta> findByCodigoAlerta(String codigoAlerta);

    List<Alerta> findByUsuarioDestinatario(Usuario usuarioDestinatario);

    Page<Alerta> findByUsuarioDestinatario(Usuario usuarioDestinatario, Pageable pageable);

    List<Alerta> findByUsuarioDestinatarioAndStatus(Usuario usuarioDestinatario, Alerta.StatusAlerta status);

    List<Alerta> findByCaso(Caso caso);

    List<Alerta> findByMedidaProtetiva(MedidaProtetiva medidaProtetiva);

    List<Alerta> findByOcorrencia(Ocorrencia ocorrencia);

    List<Alerta> findByTipoAlerta(Alerta.TipoAlerta tipoAlerta);

    List<Alerta> findByPrioridade(Alerta.Prioridade prioridade);

    List<Alerta> findByStatus(Alerta.StatusAlerta status);

    Page<Alerta> findByUsuarioDestinatarioAndStatus(Usuario usuarioDestinatario, Alerta.StatusAlerta status, Pageable pageable);

    Page<Alerta> findByStatus(Alerta.StatusAlerta status, Pageable pageable);

    long countByUsuarioDestinatarioAndStatus(Usuario usuarioDestinatario, Alerta.StatusAlerta status);

    boolean existsByCodigoAlerta(String codigoAlerta);
}