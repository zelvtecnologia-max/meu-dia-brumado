package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.MedidaProtetiva;
import com.sistema.monitoramento.model.Ronda;
import com.sistema.monitoramento.model.Usuario;
import com.sistema.monitoramento.repository.MedidaProtetivaRepository;
import com.sistema.monitoramento.repository.RondaRepository;
import com.sistema.monitoramento.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rondas")
@RequiredArgsConstructor
public class RondaController {

    private final RondaRepository repository;
    private final MedidaProtetivaRepository medidaRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Ronda> criar(@Valid @RequestBody Ronda ronda) {
        if (repository.existsByCodigoRonda(ronda.getCodigoRonda())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        validarRelacionamentos(ronda);
        Ronda salva = repository.save(ronda);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ronda> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Ronda>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Ronda>> listarPorStatus(@PathVariable Ronda.StatusRonda status, Pageable pageable) {
        return ResponseEntity.ok(repository.findByStatus(status, pageable));
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<Ronda>> listarPorData(@PathVariable LocalDate data) {
        return ResponseEntity.ok(repository.findByDataRonda(data));
    }

    @GetMapping("/periodo")
    public ResponseEntity<Page<Ronda>> listarPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            Pageable pageable) {
        return ResponseEntity.ok(repository.findByDataRondaBetween(inicio, fim, pageable));
    }

    @GetMapping("/executor/{executorId}")
    public ResponseEntity<Page<Ronda>> listarPorExecutor(@PathVariable Long executorId, Pageable pageable) {
        return usuarioRepository.findById(executorId)
                .map(executor -> ResponseEntity.ok(repository.findByUsuarioExecutor(executor, pageable)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hoje")
    public ResponseEntity<List<Ronda>> listarAgendadasParaHoje() {
        List<Ronda.StatusRonda> statuses = List.of(
                Ronda.StatusRonda.PLANEJADA,
                Ronda.StatusRonda.EM_ANDAMENTO
        );
        return ResponseEntity.ok(repository.findAgendadasParaHoje(LocalDate.now(), statuses));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Ronda> buscarPorCodigo(@PathVariable String codigo) {
        return repository.findByCodigoRonda(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ronda> atualizar(@PathVariable Long id, @Valid @RequestBody Ronda ronda) {
        return repository.findById(id)
                .map(existente -> {
                    ronda.setId(id);
                    validarRelacionamentos(ronda);
                    return ResponseEntity.ok(repository.save(ronda));
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

    private void validarRelacionamentos(Ronda ronda) {
        if (ronda.getMedidaProtetiva() != null && ronda.getMedidaProtetiva().getId() != null) {
            MedidaProtetiva medida = medidaRepository.findById(ronda.getMedidaProtetiva().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Medida protetiva não encontrada"));
            ronda.setMedidaProtetiva(medida);
        }
        if (ronda.getUsuarioResponsavel() != null && ronda.getUsuarioResponsavel().getId() != null) {
            Usuario responsavel = usuarioRepository.findById(ronda.getUsuarioResponsavel().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário responsável não encontrado"));
            ronda.setUsuarioResponsavel(responsavel);
        }
        if (ronda.getUsuarioExecutor() != null && ronda.getUsuarioExecutor().getId() != null) {
            Usuario executor = usuarioRepository.findById(ronda.getUsuarioExecutor().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário executor não encontrado"));
            ronda.setUsuarioExecutor(executor);
        }
    }
}