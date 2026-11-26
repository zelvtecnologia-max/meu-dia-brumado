package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.Pessoa;
import com.sistema.monitoramento.model.Usuario;
import com.sistema.monitoramento.repository.CasoRepository;
import com.sistema.monitoramento.repository.PessoaRepository;
import com.sistema.monitoramento.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/casos")
@RequiredArgsConstructor
public class CasoController {

    private final CasoRepository repository;
    private final PessoaRepository pessoaRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Caso> criar(@Valid @RequestBody Caso caso) {
        if (repository.existsByNumeroCaso(caso.getNumeroCaso())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        validarRelacionamentos(caso);
        Caso salvo = repository.save(caso);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caso> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Caso>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<Caso>> buscar(@RequestParam String termo, Pageable pageable) {
        return ResponseEntity.ok(repository.buscarPorTermo(termo, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Caso>> listarPorStatus(@PathVariable Caso.StatusCaso status, Pageable pageable) {
        return ResponseEntity.ok(repository.findByStatus(status, pageable));
    }

    @GetMapping("/vitima/{vitimaId}")
    public ResponseEntity<Page<Caso>> listarPorVitima(@PathVariable Long vitimaId, Pageable pageable) {
        return pessoaRepository.findById(vitimaId)
                .map(vitima -> ResponseEntity.ok(repository.findByVitima(vitima, pageable)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/grau-risco/{grau}")
    public ResponseEntity<List<Caso>> listarPorGrauRisco(@PathVariable Caso.GrauRisco grau) {
        return ResponseEntity.ok(repository.findByGrauRisco(grau));
    }

    @GetMapping("/numero/{numeroCaso}")
    public ResponseEntity<Caso> buscarPorNumero(@PathVariable String numeroCaso) {
        return repository.findByNumeroCaso(numeroCaso)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caso> atualizar(@PathVariable Long id, @Valid @RequestBody Caso caso) {
        return repository.findById(id)
                .map(existente -> {
                    caso.setId(id);
                    validarRelacionamentos(caso);
                    return ResponseEntity.ok(repository.save(caso));
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

    private void validarRelacionamentos(Caso caso) {
        if (caso.getVitima() != null && caso.getVitima().getId() != null) {
            Pessoa vitima = pessoaRepository.findById(caso.getVitima().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Vítima não encontrada"));
            caso.setVitima(vitima);
        }
        if (caso.getAgressorPrincipal() != null && caso.getAgressorPrincipal().getId() != null) {
            Pessoa agressor = pessoaRepository.findById(caso.getAgressorPrincipal().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Agressor não encontrado"));
            caso.setAgressorPrincipal(agressor);
        }
        if (caso.getResponsavel() != null && caso.getResponsavel().getId() != null) {
            Usuario responsavel = usuarioRepository.findById(caso.getResponsavel().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));
            caso.setResponsavel(responsavel);
        }
    }
}