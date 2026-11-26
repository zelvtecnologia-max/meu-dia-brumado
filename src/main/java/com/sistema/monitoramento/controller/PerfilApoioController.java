package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.PerfilApoio;
import com.sistema.monitoramento.repository.PerfilApoioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfis-apoio")
@RequiredArgsConstructor
public class PerfilApoioController {

    private final PerfilApoioRepository repository;

    @PostMapping
    public ResponseEntity<PerfilApoio> criar(@Valid @RequestBody PerfilApoio perfil) {
        PerfilApoio salvo = repository.save(perfil);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilApoio> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuaria/{usuariaId}")
    public ResponseEntity<PerfilApoio> buscarPorUsuaria(@PathVariable Integer usuariaId) {
        return repository.findByUsuariaIdUsuaria(usuariaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilApoio> atualizar(@PathVariable Integer id, @Valid @RequestBody PerfilApoio perfil) {
        return repository.findById(id)
                .map(existente -> {
                    perfil.setIdPerfilApoio(id);
                    return ResponseEntity.ok(repository.save(perfil));
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

    @PostMapping("/{id}/organizar")
    public void organizarInformacoes(@PathVariable Integer id) {
        repository.findById(id).ifPresent(PerfilApoio::organizarInformacoes);
    }

    @PostMapping("/{id}/contatos")
    public void adicionarContato(@PathVariable Integer id) {
        repository.findById(id).ifPresent(PerfilApoio::adicionarContato);
    }
}