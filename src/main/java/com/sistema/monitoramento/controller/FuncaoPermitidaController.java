package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.FuncaoPermitida;
import com.sistema.monitoramento.repository.FuncaoPermitidaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcoes-permitidas")
@RequiredArgsConstructor
public class FuncaoPermitidaController {

    private final FuncaoPermitidaRepository repository;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<FuncaoPermitida> criar(@Valid @RequestBody FuncaoPermitida funcao) {
        FuncaoPermitida salva = repository.save(funcao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<FuncaoPermitida> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<FuncaoPermitida>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/modulo/{modulo}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<FuncaoPermitida>> listarPorModulo(@PathVariable String modulo) {
        return ResponseEntity.ok(repository.findByModulo(modulo));
    }

    @GetMapping("/ativas")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<FuncaoPermitida>> listarAtivas() {
        return ResponseEntity.ok(repository.findByAtivoTrue());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<FuncaoPermitida> atualizar(@PathVariable Integer id, @Valid @RequestBody FuncaoPermitida funcao) {
        return repository.findById(id)
                .map(existente -> {
                    funcao.setIdFuncao(id);
                    return ResponseEntity.ok(repository.save(funcao));
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
}