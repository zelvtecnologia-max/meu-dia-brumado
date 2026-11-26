package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.RegistroAuditoria;
import com.sistema.monitoramento.repository.RegistroAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/registros-auditoria")
@RequiredArgsConstructor
public class RegistroAuditoriaController {

    private final RegistroAuditoriaRepository repository;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RegistroAuditoria> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<RegistroAuditoria>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/usuario/{usuarioId}/tipo/{tipoUsuario}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<RegistroAuditoria>> listarPorUsuarioETipo(
            @PathVariable Integer usuarioId,
            @PathVariable String tipoUsuario) {
        return ResponseEntity.ok(repository.findByIdUsuarioAndTipoUsuario(usuarioId, tipoUsuario));
    }

    @GetMapping("/entidade/{entidade}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<RegistroAuditoria>> listarPorEntidade(@PathVariable String entidade) {
        return ResponseEntity.ok(repository.findByEntidadeAfetada(entidade));
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<RegistroAuditoria>> listarPorPeriodo(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fim) {
        return ResponseEntity.ok(repository.findByDataHoraBetween(inicio, fim));
    }

    @GetMapping("/acao/{acao}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<RegistroAuditoria>> listarPorAcao(@PathVariable String acao) {
        return ResponseEntity.ok(repository.findByAcao(acao));
    }

    @GetMapping("/erros")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<RegistroAuditoria>> listarErros() {
        return ResponseEntity.ok(repository.findBySucessoFalse());
    }

    @GetMapping("/{id}/consultar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void consultar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(RegistroAuditoria::consultar);
    }
}