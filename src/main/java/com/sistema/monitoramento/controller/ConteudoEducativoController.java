package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.ConteudoEducativo;
import com.sistema.monitoramento.repository.ConteudoEducativoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conteudos-educativos")
@RequiredArgsConstructor
public class ConteudoEducativoController {

    private final ConteudoEducativoRepository repository;

    @PostMapping
    public ResponseEntity<ConteudoEducativo> criar(@Valid @RequestBody ConteudoEducativo conteudo) {
        ConteudoEducativo salvo = repository.save(conteudo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoEducativo> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ConteudoEducativo>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ConteudoEducativo>> listarPorStatus(@PathVariable ConteudoEducativo.StatusConteudo status) {
        return ResponseEntity.ok(repository.findByStatus(status));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ConteudoEducativo>> listarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(repository.findByCategoria(categoria));
    }

    @GetMapping("/publicados")
    public ResponseEntity<List<ConteudoEducativo>> listarPublicados() {
        return ResponseEntity.ok(repository.findByStatus(ConteudoEducativo.StatusConteudo.PUBLICADO));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ConteudoEducativo>> buscar(@RequestParam String titulo) {
        return ResponseEntity.ok(repository.findByTituloContainingIgnoreCase(titulo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConteudoEducativo> atualizar(@PathVariable Integer id, @Valid @RequestBody ConteudoEducativo conteudo) {
        return repository.findById(id)
                .map(existente -> {
                    conteudo.setIdConteudo(id);
                    return ResponseEntity.ok(repository.save(conteudo));
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

    @PostMapping("/{id}/cadastrar")
    public void cadastrar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ConteudoEducativo::cadastrar);
    }

    @PutMapping("/{id}/atualizar")
    public void atualizarConteudo(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ConteudoEducativo::atualizar);
    }

    @GetMapping("/{id}/consultar")
    public void consultar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ConteudoEducativo::consultar);
    }
}