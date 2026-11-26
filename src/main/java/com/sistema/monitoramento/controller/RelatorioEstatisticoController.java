package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.RelatorioEstatistico;
import com.sistema.monitoramento.repository.RelatorioEstatisticoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios-estatisticos")
@RequiredArgsConstructor
public class RelatorioEstatisticoController {

    private final RelatorioEstatisticoRepository repository;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RelatorioEstatistico> criar(@Valid @RequestBody RelatorioEstatistico relatorio) {
        RelatorioEstatistico salvo = repository.save(relatorio);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RelatorioEstatistico> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<RelatorioEstatistico>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/periodo/{periodo}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<RelatorioEstatistico>> listarPorPeriodo(@PathVariable String periodo) {
        return ResponseEntity.ok(repository.findByPeriodo(periodo));
    }

    @GetMapping("/data")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<RelatorioEstatistico>> listarPorPeriodoData(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        return ResponseEntity.ok(repository.findByDataGeracaoBetween(inicio, fim));
    }

    @GetMapping("/gerado-por/{usuarioId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<RelatorioEstatistico>> listarPorGeradoPor(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(repository.findByGeradoPorIdUsuario(usuarioId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<RelatorioEstatistico>> listarPorStatus(@PathVariable RelatorioEstatistico.StatusRelatorio status) {
        return ResponseEntity.ok(repository.findByStatus(status));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RelatorioEstatistico> atualizar(@PathVariable Integer id, @Valid @RequestBody RelatorioEstatistico relatorio) {
        return repository.findById(id)
                .map(existente -> {
                    relatorio.setIdRelatorio(id);
                    return ResponseEntity.ok(repository.save(relatorio));
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

    @PostMapping("/{id}/gerar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RelatorioEstatistico> gerar(@PathVariable Integer id) {
        return repository.findById(id)
                .map(relatorio -> {
                    relatorio.gerar();
                    return ResponseEntity.ok(repository.save(relatorio));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/consultar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void consultar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(RelatorioEstatistico::consultar);
    }
}