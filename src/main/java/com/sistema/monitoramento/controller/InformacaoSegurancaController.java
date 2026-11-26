package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.InformacaoSeguranca;
import com.sistema.monitoramento.repository.InformacaoSegurancaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/informacoes-seguranca")
@RequiredArgsConstructor
public class InformacaoSegurancaController {

    private final InformacaoSegurancaRepository repository;

    @PostMapping
    public ResponseEntity<InformacaoSeguranca> criar(@Valid @RequestBody InformacaoSeguranca info) {
        InformacaoSeguranca salva = repository.save(info);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformacaoSeguranca> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuaria/{usuariaId}")
    public ResponseEntity<List<InformacaoSeguranca>> listarPorUsuaria(@PathVariable Integer usuariaId) {
        return ResponseEntity.ok(repository.findByUsuariaIdUsuariaAndAtivoTrue(usuariaId));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<InformacaoSeguranca>> listarPorCategoria(@PathVariable InformacaoSeguranca.CategoriaInformacao categoria) {
        return ResponseEntity.ok(repository.findByCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InformacaoSeguranca> atualizar(@PathVariable Integer id, @Valid @RequestBody InformacaoSeguranca info) {
        return repository.findById(id)
                .map(existente -> {
                    info.setIdInformacao(id);
                    return ResponseEntity.ok(repository.save(info));
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

    @PostMapping("/{id}/registrar")
    public void registrar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(InformacaoSeguranca::registrar);
    }

    @PutMapping("/{id}/atualizar")
    public void atualizarInfo(@PathVariable Integer id) {
        repository.findById(id).ifPresent(InformacaoSeguranca::atualizar);
    }

    @GetMapping("/{id}/consultar")
    public void consultar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(InformacaoSeguranca::consultar);
    }
}