package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Pessoa;
import com.sistema.monitoramento.repository.PessoaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaRepository repository;

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa) {
        if (repository.existsByCpf(pessoa.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (pessoa.getEmail() != null && repository.existsByEmail(pessoa.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Pessoa salva = repository.save(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Pessoa>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<Pessoa>> buscar(@RequestParam String termo, Pageable pageable) {
        return ResponseEntity.ok(repository.buscarPorTermo(termo, pageable));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<Page<Pessoa>> listarPorTipo(@PathVariable Pessoa.TipoPessoa tipo, Pageable pageable) {
        return ResponseEntity.ok(repository.findByTipoPessoa(tipo, pageable));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Pessoa> buscarPorCpf(@PathVariable String cpf) {
        return repository.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
        return repository.findById(id)
                .map(existente -> {
                    pessoa.setId(id);
                    return ResponseEntity.ok(repository.save(pessoa));
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
}