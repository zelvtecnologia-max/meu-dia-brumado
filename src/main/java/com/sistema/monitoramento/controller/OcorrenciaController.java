package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.MedidaProtetiva;
import com.sistema.monitoramento.model.Ocorrencia;
import com.sistema.monitoramento.model.Pessoa;
import com.sistema.monitoramento.model.Usuario;
import com.sistema.monitoramento.repository.CasoRepository;
import com.sistema.monitoramento.repository.MedidaProtetivaRepository;
import com.sistema.monitoramento.repository.OcorrenciaRepository;
import com.sistema.monitoramento.repository.PessoaRepository;
import com.sistema.monitoramento.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaRepository repository;
    private final CasoRepository casoRepository;
    private final MedidaProtetivaRepository medidaRepository;
    private final PessoaRepository pessoaRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Ocorrencia> criar(@Valid @RequestBody Ocorrencia ocorrencia) {
        if (repository.existsByNumeroOcorrencia(ocorrencia.getNumeroOcorrencia())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        validarRelacionamentos(ocorrencia);
        Ocorrencia salva = repository.save(ocorrencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Ocorrencia>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<Ocorrencia>> buscar(@RequestParam String termo, Pageable pageable) {
        return ResponseEntity.ok(repository.buscarPorTermo(termo, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Ocorrencia>> listarPorStatus(@PathVariable Ocorrencia.StatusOcorrencia status, Pageable pageable) {
        return ResponseEntity.ok(repository.findByStatus(status, pageable));
    }

    @GetMapping("/gravidade/{gravidade}")
    public ResponseEntity<List<Ocorrencia>> listarPorGravidade(@PathVariable Ocorrencia.Gravidade gravidade) {
        return ResponseEntity.ok(repository.findByGravidade(gravidade));
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<Ocorrencia>> listarPorData(@PathVariable LocalDate data) {
        return ResponseEntity.ok(repository.findByDataOcorrencia(data));
    }

    @GetMapping("/periodo")
    public ResponseEntity<Page<Ocorrencia>> listarPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            Pageable pageable) {
        return ResponseEntity.ok(repository.findByDataOcorrenciaBetween(inicio, fim, pageable));
    }

    @GetMapping("/caso/{casoId}")
    public ResponseEntity<Page<Ocorrencia>> listarPorCaso(@PathVariable Long casoId, Pageable pageable) {
        return casoRepository.findById(casoId)
                .map(caso -> ResponseEntity.ok(repository.findByCaso(caso, pageable)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<Ocorrencia> buscarPorNumero(@PathVariable String numero) {
        return repository.findByNumeroOcorrencia(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ocorrencia> atualizar(@PathVariable Long id, @Valid @RequestBody Ocorrencia ocorrencia) {
        return repository.findById(id)
                .map(existente -> {
                    ocorrencia.setId(id);
                    validarRelacionamentos(ocorrencia);
                    return ResponseEntity.ok(repository.save(ocorrencia));
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

    private void validarRelacionamentos(Ocorrencia ocorrencia) {
        if (ocorrencia.getCaso() != null && ocorrencia.getCaso().getId() != null) {
            Caso caso = casoRepository.findById(ocorrencia.getCaso().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Caso não encontrado"));
            ocorrencia.setCaso(caso);
        }
        if (ocorrencia.getMedidaProtetiva() != null && ocorrencia.getMedidaProtetiva().getId() != null) {
            MedidaProtetiva medida = medidaRepository.findById(ocorrencia.getMedidaProtetiva().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Medida protetiva não encontrada"));
            ocorrencia.setMedidaProtetiva(medida);
        }
        if (ocorrencia.getVitima() != null && ocorrencia.getVitima().getId() != null) {
            Pessoa vitima = pessoaRepository.findById(ocorrencia.getVitima().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Vítima não encontrada"));
            ocorrencia.setVitima(vitima);
        }
        if (ocorrencia.getAgressor() != null && ocorrencia.getAgressor().getId() != null) {
            Pessoa agressor = pessoaRepository.findById(ocorrencia.getAgressor().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Agressor não encontrado"));
            ocorrencia.setAgressor(agressor);
        }
        if (ocorrencia.getUsuarioRegistro() != null && ocorrencia.getUsuarioRegistro().getId() != null) {
            Usuario usuario = usuarioRepository.findById(ocorrencia.getUsuarioRegistro().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário de registro não encontrado"));
            ocorrencia.setUsuarioRegistro(usuario);
        }
    }
}