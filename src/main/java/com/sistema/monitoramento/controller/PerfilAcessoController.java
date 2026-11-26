package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.PerfilAcesso;
import com.sistema.monitoramento.repository.PerfilAcessoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfis-acesso")
@RequiredArgsConstructor
public class PerfilAcessoController {

    private final PerfilAcessoRepository repository;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PerfilAcesso> criar(@Valid @RequestBody PerfilAcesso perfil) {
        Optional<PerfilAcesso> existente = repository.findByNome(perfil.getNome());
        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        PerfilAcesso salvo = repository.save(perfil);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PerfilAcesso> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<PerfilAcesso>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/ativos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<PerfilAcesso>> listarAtivos() {
        return ResponseEntity.ok(repository.findByAtivoTrue());
    }

    @GetMapping("/nome/{nome}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PerfilAcesso> buscarPorNome(@PathVariable String nome) {
        return repository.findByNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PerfilAcesso> atualizar(@PathVariable Integer id, @Valid @RequestBody PerfilAcesso perfil) {
        return repository.findById(id)
                .map(existente -> {
                    perfil.setIdPerfil(id);
                    return ResponseEntity.ok(repository.save(perfil));
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

    @PostMapping("/{id}/definir-permissoes")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void definirPermissoes(@PathVariable Integer id) {
        repository.findById(id).ifPresent(PerfilAcesso::definirPermissoes);
    }

    @PutMapping("/{id}/atualizar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void atualizarPerfil(@PathVariable Integer id) {
        repository.findById(id).ifPresent(PerfilAcesso::atualizar);
    }
}