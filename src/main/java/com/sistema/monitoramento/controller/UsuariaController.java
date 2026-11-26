package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Usuaria;
import com.sistema.monitoramento.repository.UsuariaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarias")
@RequiredArgsConstructor
public class UsuariaController {

    private final UsuariaRepository repository;

    @PostMapping
    public ResponseEntity<Usuaria> criar(@Valid @RequestBody Usuaria usuaria) {
        if (repository.existsByEmail(usuaria.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Usuaria salva = repository.save(usuaria);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuaria> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Usuaria>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Usuaria>> buscar(@RequestParam String termo) {
        return ResponseEntity.ok(repository.buscarPorTermo(termo));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Usuaria>> listarPorStatus(@PathVariable Usuaria.StatusUsuaria status) {
        return ResponseEntity.ok(repository.findByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuaria> atualizar(@PathVariable Integer id, @Valid @RequestBody Usuaria usuaria) {
        return repository.findById(id)
                .map(existente -> {
                    usuaria.setIdUsuaria(id);
                    return ResponseEntity.ok(repository.save(usuaria));
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

    @GetMapping("/{id}/informacoes")
    public void consultarInformacoes(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Usuaria::consultarInformacoes);
    }

    @PutMapping("/{id}/perfil-apoio")
    public void gerenciarPerfilApoio(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Usuaria::gerenciarPerfilApoio);
    }
}