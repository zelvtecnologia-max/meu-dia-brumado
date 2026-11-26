package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Administrador;
import com.sistema.monitoramento.repository.AdministradorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Administrador> criar(@Valid @RequestBody Administrador admin) {
        if (repository.existsByEmail(admin.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        admin.setSenhaHash(passwordEncoder.encode(admin.getSenhaHash()));
        Administrador salvo = repository.save(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Administrador> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<Administrador>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/gerenciar-usuarios")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Administrador>> listarGerenciadoresUsuarios() {
        return ResponseEntity.ok(repository.findByPodeGerenciarUsuariosTrue());
    }

    @GetMapping("/nivel/{nivel}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Administrador>> listarPorNivel(@PathVariable String nivel) {
        return ResponseEntity.ok(repository.findByNivelAcesso(nivel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Administrador> atualizar(@PathVariable Integer id, @Valid @RequestBody Administrador admin) {
        return repository.findById(id)
                .map(existente -> {
                    admin.setIdUsuario(id);
                    if (admin.getSenhaHash() != null && !admin.getSenhaHash().startsWith("$2a$")) {
                        admin.setSenhaHash(passwordEncoder.encode(admin.getSenhaHash()));
                    }
                    return ResponseEntity.ok(repository.save(admin));
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

    @PostMapping("/{id}/gerenciar-conteudos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void gerenciarConteudos(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Administrador::gerenciarConteudos);
    }

    @PostMapping("/{id}/gerenciar-diretorio")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void gerenciarDiretorio(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Administrador::gerenciarDiretorio);
    }

    @PostMapping("/{id}/gerenciar-usuarios")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void gerenciarUsuarios(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Administrador::gerenciarUsuarios);
    }

    @GetMapping("/{id}/relatorios")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void consultarRelatorios(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Administrador::consultarRelatorios);
    }

    @GetMapping("/{id}/auditoria")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void consultarAuditoria(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Administrador::consultarAuditoria);
    }

    @PostMapping("/{id}/gerenciar-configuracoes")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void gerenciarConfiguracoes(@PathVariable Integer id) {
        repository.findById(id).ifPresent(Administrador::gerenciarConfiguracoes);
    }
}