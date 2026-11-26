package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.ContatoConfianca;
import com.sistema.monitoramento.repository.ContatoConfiancaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contatos-confianca")
@RequiredArgsConstructor
public class ContatoConfiancaController {

    private final ContatoConfiancaRepository repository;

    @PostMapping
    public ResponseEntity<ContatoConfianca> criar(@Valid @RequestBody ContatoConfianca contato) {
        ContatoConfianca salvo = repository.save(contato);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoConfianca> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuaria/{usuariaId}")
    public ResponseEntity<List<ContatoConfianca>> listarPorUsuaria(@PathVariable Integer usuariaId) {
        return ResponseEntity.ok(repository.findByUsuariaIdUsuariaAndAtivoTrue(usuariaId));
    }

    @GetMapping("/usuaria/{usuariaId}/principal")
    public ResponseEntity<List<ContatoConfianca>> listarPrincipais(@PathVariable Integer usuariaId) {
        return ResponseEntity.ok(repository.findByUsuariaIdUsuariaAndContatoPrincipalTrue(usuariaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoConfianca> atualizar(@PathVariable Integer id, @Valid @RequestBody ContatoConfianca contato) {
        return repository.findById(id)
                .map(existente -> {
                    contato.setIdContato(id);
                    return ResponseEntity.ok(repository.save(contato));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        return repository.findById(id)
                .map(contato -> {
                    contato.remover();
                    repository.save(contato);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/adicionar")
    public void adicionar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ContatoConfianca::adicionar);
    }

    @PutMapping("/{id}/atualizar")
    public void atualizarContato(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ContatoConfianca::atualizar);
    }

    @DeleteMapping("/{id}/remover")
    public void removerContato(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ContatoConfianca::remover);
    }
}