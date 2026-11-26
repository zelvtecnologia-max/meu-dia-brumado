package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Instituicao;
import com.sistema.monitoramento.repository.InstituicaoRepository;
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
@RequestMapping("/api/instituicoes")
@RequiredArgsConstructor
public class InstituicaoController {

    private final InstituicaoRepository repository;

    @PostMapping
    public ResponseEntity<Instituicao> criar(@Valid @RequestBody Instituicao instituicao) {
        Optional<Instituicao> existente = repository.findByNome(instituicao.getNome());
        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Instituicao salva = repository.save(instituicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instituicao> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Instituicao>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<Instituicao>> listarAtivas() {
        return ResponseEntity.ok(repository.findByAtivoTrue());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Instituicao>> listarPorTipo(@PathVariable Instituicao.TipoInstituicao tipo) {
        return ResponseEntity.ok(repository.findByTipoInstituicao(tipo));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Instituicao>> buscar(@RequestParam String nome) {
        return ResponseEntity.ok(repository.findByNomeContainingIgnoreCase(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instituicao> atualizar(@PathVariable Integer id, @Valid @RequestBody Instituicao instituicao) {
        return repository.findById(id)
                .map(existente -> {
                    instituicao.setIdInstituicao(id);
                    return ResponseEntity.ok(repository.save(instituicao));
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
        repository.findById(id).ifPresent(Instituicao::cadastrar);
    }

    @PutMapping("/{id}/atualizar")
    public void atualizarInstituicao(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Instituicao::atualizar);
    }

    @GetMapping("/{id}/consultar")
    public void consultar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Instituicao::consultar);
    }
}