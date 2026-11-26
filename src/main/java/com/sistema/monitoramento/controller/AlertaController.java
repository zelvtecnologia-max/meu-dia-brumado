package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Alerta;
import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.MedidaProtetiva;
import com.sistema.monitoramento.model.Ocorrencia;
import com.sistema.monitoramento.model.Usuario;
import com.sistema.monitoramento.repository.AlertaRepository;
import com.sistema.monitoramento.repository.CasoRepository;
import com.sistema.monitoramento.repository.MedidaProtetivaRepository;
import com.sistema.monitoramento.repository.OcorrenciaRepository;
import com.sistema.monitoramento.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final CasoRepository casoRepository;
    private final MedidaProtetivaRepository medidaRepository;
    private final OcorrenciaRepository ocorrenciaRepository;

    @PostMapping
    public ResponseEntity<Alerta> criar(@Valid @RequestBody Alerta alerta) {
        if (repository.existsByCodigoAlerta(alerta.getCodigoAlerta())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        validarRelacionamentos(alerta);
        Alerta salvo = repository.save(alerta);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Alerta>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/meus")
    public ResponseEntity<Page<Alerta>> listarMeus(
            @RequestParam Long usuarioId,
            @RequestParam(required = false) Alerta.StatusAlerta status,
            Pageable pageable) {
        return usuarioRepository.findById(usuarioId)
                .map(usuario -> {
                    if (status != null) {
                        return ResponseEntity.ok(repository.findByUsuarioDestinatarioAndStatus(usuario, status, pageable));
                    }
                    return ResponseEntity.ok(repository.findByUsuarioDestinatario(usuario, pageable));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nao-lidos/{usuarioId}")
    public ResponseEntity<Long> contarNaoLidos(@PathVariable Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(usuario -> ResponseEntity.ok(repository.countByUsuarioDestinatarioAndStatus(usuario, Alerta.StatusAlerta.NAO_LIDO)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Alerta>> listarPorStatus(@PathVariable Alerta.StatusAlerta status, Pageable pageable) {
        return ResponseEntity.ok(repository.findByStatus(status, pageable));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Alerta>> listarPorTipo(@PathVariable Alerta.TipoAlerta tipo) {
        return ResponseEntity.ok(repository.findByTipoAlerta(tipo));
    }

    @GetMapping("/prioridade/{prioridade}")
    public ResponseEntity<List<Alerta>> listarPorPrioridade(@PathVariable Alerta.Prioridade prioridade) {
        return ResponseEntity.ok(repository.findByPrioridade(prioridade));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Alerta> buscarPorCodigo(@PathVariable String codigo) {
        return repository.findByCodigoAlerta(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/ler")
    public ResponseEntity<Alerta> marcarComoLido(@PathVariable Long id) {
        return repository.findById(id)
                .map(alerta -> {
                    alerta.setStatus(Alerta.StatusAlerta.LIDO);
                    alerta.setDataLeitura(LocalDateTime.now());
                    return ResponseEntity.ok(repository.save(alerta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/responder")
    public ResponseEntity<Alerta> responder(@PathVariable Long id, @RequestBody String resposta) {
        return repository.findById(id)
                .map(alerta -> {
                    alerta.setStatus(Alerta.StatusAlerta.RESOLVIDO);
                    alerta.setDataResposta(LocalDateTime.now());
                    alerta.setResposta(resposta);
                    return ResponseEntity.ok(repository.save(alerta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alerta> atualizar(@PathVariable Long id, @Valid @RequestBody Alerta alerta) {
        return repository.findById(id)
                .map(existente -> {
                    alerta.setId(id);
                    validarRelacionamentos(alerta);
                    return ResponseEntity.ok(repository.save(alerta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void validarRelacionamentos(Alerta alerta) {
        if (alerta.getUsuarioDestinatario() != null && alerta.getUsuarioDestinatario().getId() != null) {
            Usuario destinatario = usuarioRepository.findById(alerta.getUsuarioDestinatario().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário destinatário não encontrado"));
            alerta.setUsuarioDestinatario(destinatario);
        }
        if (alerta.getUsuarioRemetente() != null && alerta.getUsuarioRemetente().getId() != null) {
            Usuario remetente = usuarioRepository.findById(alerta.getUsuarioRemetente().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário remetente não encontrado"));
            alerta.setUsuarioRemetente(remetente);
        }
        if (alerta.getCaso() != null && alerta.getCaso().getId() != null) {
            Caso caso = casoRepository.findById(alerta.getCaso().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Caso não encontrado"));
            alerta.setCaso(caso);
        }
        if (alerta.getMedidaProtetiva() != null && alerta.getMedidaProtetiva().getId() != null) {
            MedidaProtetiva medida = medidaRepository.findById(alerta.getMedidaProtetiva().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Medida protetiva não encontrada"));
            alerta.setMedidaProtetiva(medida);
        }
        if (alerta.getOcorrencia() != null && alerta.getOcorrencia().getId() != null) {
            Ocorrencia ocorrencia = ocorrenciaRepository.findById(alerta.getOcorrencia().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Ocorrência não encontrada"));
            alerta.setOcorrencia(ocorrencia);
        }
    }
}