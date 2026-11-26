package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.ProfissionalAutorizado;
import com.sistema.monitoramento.repository.ProfissionalAutorizadoRepository;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/profissionais")
@RequiredArgsConstructor
public class ProfissionalAutorizadoController {

    private final ProfissionalAutorizadoRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ProfissionalAutorizado> criar(@Valid @RequestBody ProfissionalAutorizado profissional) {
        if (repository.existsByEmail(profissional.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        profissional.setSenhaHash(passwordEncoder.encode(profissional.getSenhaHash()));
        ProfissionalAutorizado salvo = repository.save(profissional);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('PROFISSIONAL_AUTORIZADO')")
    public ResponseEntity<ProfissionalAutorizado> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<ProfissionalAutorizado>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/orgao/{orgao}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ProfissionalAutorizado>> listarPorOrgao(@PathVariable String orgao) {
        return ResponseEntity.ok(repository.findByOrgaoLotacao(orgao));
    }

    @GetMapping("/especialidade/{especialidade}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ProfissionalAutorizado>> listarPorEspecialidade(@PathVariable String especialidade) {
        return ResponseEntity.ok(repository.findByEspecialidade(especialidade));
    }

    @GetMapping("/registro/{registro}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ProfissionalAutorizado> buscarPorRegistro(@PathVariable String registro) {
        return repository.findByRegistroProfissional(registro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ProfissionalAutorizado> atualizar(@PathVariable Integer id, @Valid @RequestBody ProfissionalAutorizado profissional) {
        return repository.findById(id)
                .map(existente -> {
                    profissional.setIdUsuario(id);
                    if (profissional.getSenhaHash() != null && !profissional.getSenhaHash().startsWith("$2a$")) {
                        profissional.setSenhaHash(passwordEncoder.encode(profissional.getSenhaHash()));
                    }
                    return ResponseEntity.ok(repository.save(profissional));
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

    @GetMapping("/{id}/funcoes-permitidas")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('PROFISSIONAL_AUTORIZADO')")
    public void consultarFuncoesPermitidas(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ProfissionalAutorizado::consultarFuncoesPermitidas);
    }
}