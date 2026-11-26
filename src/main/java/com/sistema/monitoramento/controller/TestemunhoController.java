package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Testemunho;
import com.sistema.monitoramento.repository.TestemunhoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testemunhos")
@RequiredArgsConstructor
public class TestemunhoController {

    private final TestemunhoRepository repository;

    @PostMapping
    public ResponseEntity<Testemunho> criar(@Valid @RequestBody Testemunho testemunho) {
        Testemunho salvo = repository.save(testemunho);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Testemunho> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Testemunho>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Testemunho>> listarPorStatus(@PathVariable Testemunho.StatusTestemunho status) {
        return ResponseEntity.ok(repository.findByStatusOrderByDataPublicacaoDesc(status));
    }

    @GetMapping("/publicados")
    public ResponseEntity<List<Testemunho>> listarPublicados() {
        return ResponseEntity.ok(repository.findByStatusOrderByDataPublicacaoDesc(Testemunho.StatusTestemunho.PUBLICADO));
    }

    @GetMapping("/anonimos")
    public ResponseEntity<List<Testemunho>> listarAnonimos() {
        return ResponseEntity.ok(repository.findByAutorAnonimoTrue());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Testemunho> atualizar(@PathVariable Integer id, @Valid @RequestBody Testemunho testemunho) {
        return repository.findById(id)
                .map(existente -> {
                    testemunho.setIdTestemunho(id);
                    return ResponseEntity.ok(repository.save(testemunho));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/publicar")
    public ResponseEntity<Testemunho> publicar(@PathVariable Integer id) {
        return repository.findById(id)
                .map(testemunho -> {
                    testemunho.publicar();
                    return ResponseEntity.ok(repository.save(testemunho));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/consultar")
    public void consultar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Testemunho::consultar);
    }
}