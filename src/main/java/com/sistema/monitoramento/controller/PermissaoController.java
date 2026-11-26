package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Permissao;
import com.sistema.monitoramento.repository.PermissaoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissoes")
@RequiredArgsConstructor
public class PermissaoController {

    private final PermissaoRepository repository;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Permissao> criar(@Valid @RequestBody Permissao permissao) {
        Optional<Permissao> existente = repository.findByNome(permissao.getNome());
        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Permissao salva = repository.save(permissao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Permissao> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<Permissao>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/recurso/{recurso}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Permissao>> listarPorRecurso(@PathVariable String recurso) {
        return ResponseEntity.ok(repository.findByRecurso(recurso));
    }

    @GetMapping("/recurso/{recurso}/acao/{acao}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Permissao>> listarPorRecursoEAcao(@PathVariable String recurso, @PathVariable String acao) {
        return ResponseEntity.ok(repository.findByRecursoAndAcao(recurso, acao));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Permissao> atualizar(@PathVariable Integer id, @Valid @RequestBody Permissao permissao) {
        return repository.findById(id)
                .map(existente -> {
                    permissao.setIdPermissao(id);
                    return ResponseEntity.ok(repository.save(permissao));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/verificar-acesso")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Boolean> verificarAcesso(@PathVariable Integer id) {
        return repository.findById(id)
                .map(p -> ResponseEntity.ok(p.verificarAcesso()))
                .orElse(ResponseEntity.notFound().build());
    }
}