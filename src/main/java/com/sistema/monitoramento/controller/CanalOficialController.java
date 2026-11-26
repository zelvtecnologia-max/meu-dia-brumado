package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.CanalOficial;
import com.sistema.monitoramento.repository.CanalOficialRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canais-oficiais")
@RequiredArgsConstructor
public class CanalOficialController {

    private final CanalOficialRepository repository;

    @PostMapping
    public ResponseEntity<CanalOficial> criar(@Valid @RequestBody CanalOficial canal) {
        CanalOficial salvo = repository.save(canal);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanalOficial> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CanalOficial>> listar() {
        return ResponseEntity.ok(repository.findByAtivoTrue());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CanalOficial>> buscar(@RequestParam String finalidade) {
        return ResponseEntity.ok(repository.findByFinalidadeContainingIgnoreCase(finalidade));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CanalOficial> atualizar(@PathVariable Integer id, @Valid @RequestBody CanalOficial canal) {
        return repository.findById(id)
                .map(existente -> {
                    canal.setIdCanal(id);
                    return ResponseEntity.ok(repository.save(canal));
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

    @GetMapping("/{id}/consultar")
    public void consultar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(CanalOficial::consultar);
    }

    @GetMapping("/{id}/acessar")
    public void acessarCanal(@PathVariable Integer id) {
        repository.findById(id).ifPresent(CanalOficial::acessarCanal);
    }
}