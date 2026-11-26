package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.ServicoAtendimento;
import com.sistema.monitoramento.repository.ServicoAtendimentoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos-atendimento")
@RequiredArgsConstructor
public class ServicoAtendimentoController {

    private final ServicoAtendimentoRepository repository;

    @PostMapping
    public ResponseEntity<ServicoAtendimento> criar(@Valid @RequestBody ServicoAtendimento servico) {
        ServicoAtendimento salvo = repository.save(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoAtendimento> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/instituicao/{instituicaoId}")
    public ResponseEntity<List<ServicoAtendimento>> listarPorInstituicao(@PathVariable Integer instituicaoId) {
        return ResponseEntity.ok(repository.findByInstituicaoIdInstituicaoAndAtivoTrue(instituicaoId));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ServicoAtendimento>> listarAtivos() {
        return ResponseEntity.ok(repository.findByAtivoTrue());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoAtendimento> atualizar(@PathVariable Integer id, @Valid @RequestBody ServicoAtendimento servico) {
        return repository.findById(id)
                .map(existente -> {
                    servico.setIdServico(id);
                    return ResponseEntity.ok(repository.save(servico));
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
        repository.findById(id).ifPresent(ServicoAtendimento::cadastrar);
    }

    @PutMapping("/{id}/atualizar")
    public void atualizarServico(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ServicoAtendimento::atualizar);
    }

    @GetMapping("/{id}/consultar")
    public void consultar(@PathVariable Integer id) {
        repository.findById(id).ifPresent(ServicoAtendimento::consultar);
    }
}