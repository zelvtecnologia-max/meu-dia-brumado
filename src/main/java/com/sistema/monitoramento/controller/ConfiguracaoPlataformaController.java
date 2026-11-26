package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.ConfiguracaoPlataforma;
import com.sistema.monitoramento.repository.ConfiguracaoPlataformaRepository;
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
@RequestMapping("/api/configuracoes")
@RequiredArgsConstructor
public class ConfiguracaoPlataformaController {

    private final ConfiguracaoPlataformaRepository repository;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ConfiguracaoPlataforma> criar(@Valid @RequestBody ConfiguracaoPlataforma config) {
        Optional<ConfiguracaoPlataforma> existente = repository.findByNome(config.getNome());
        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        ConfiguracaoPlataforma salva = repository.save(config);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ConfiguracaoPlataforma> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<ConfiguracaoPlataforma>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/nome/{nome}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ConfiguracaoPlataforma> buscarPorNome(@PathVariable String nome) {
        return repository.findByNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ConfiguracaoPlataforma>> listarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(repository.findByCategoria(categoria));
    }

    @GetMapping("/editaveis")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ConfiguracaoPlataforma>> listarEditaveis() {
        return ResponseEntity.ok(repository.findByEditavelTrue());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ConfiguracaoPlataforma> atualizar(@PathVariable Integer id, @Valid @RequestBody ConfiguracaoPlataforma config) {
        return repository.findById(id)
                .map(existente -> {
                    config.setIdConfiguracao(id);
                    return ResponseEntity.ok(repository.save(config));
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

    @PutMapping("/{id}/atualizar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void atualizarConfig(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ConfiguracaoPlataforma::atualizar);
    }

    @GetMapping("/{id}/consultar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void consultar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ConfiguracaoPlataforma::consultar);
    }
}