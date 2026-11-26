package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Caso;
import com.sistema.monitoramento.model.MedidaProtetiva;
import com.sistema.monitoramento.model.Pessoa;
import com.sistema.monitoramento.model.Usuario;
import com.sistema.monitoramento.repository.CasoRepository;
import com.sistema.monitoramento.repository.MedidaProtetivaRepository;
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
@RequestMapping("/api/medidas-protetivas")
@RequiredArgsConstructor
public class MedidaProtetivaController {

    private final MedidaProtetivaRepository repository;
    private final CasoRepository casoRepository;
    private final PessoaRepository pessoaRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<MedidaProtetiva> criar(@Valid @RequestBody MedidaProtetiva medida) {
        if (repository.existsByNumeroProcesso(medida.getNumeroProcesso())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        validarRelacionamentos(medida);
        MedidaProtetiva salva = repository.save(medida);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedidaProtetiva> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<MedidaProtetiva>> listar(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<MedidaProtetiva>> listarPorStatus(@PathVariable MedidaProtetiva.StatusMedida status, Pageable pageable) {
        return ResponseEntity.ok(repository.findByStatus(status, pageable));
    }

    @GetMapping("/caso/{casoId}")
    public ResponseEntity<Page<MedidaProtetiva>> listarPorCaso(@PathVariable Long casoId, Pageable pageable) {
        return casoRepository.findById(casoId)
                .map(caso -> ResponseEntity.ok(repository.findByCaso(caso, pageable)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vitima/{vitimaId}")
    public ResponseEntity<Page<MedidaProtetiva>> listarPorVitima(@PathVariable Long vitimaId, Pageable pageable) {
        return pessoaRepository.findById(vitimaId)
                .map(vitima -> ResponseEntity.ok(repository.findByVitima(vitima, pageable)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vencendo")
    public ResponseEntity<List<MedidaProtetiva>> listarVencendo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        List<MedidaProtetiva.StatusMedida> statuses = List.of(
                MedidaProtetiva.StatusMedida.CONCEBIDA,
                MedidaProtetiva.StatusMedida.EM_VIGOR
        );
        return ResponseEntity.ok(repository.findVencendoEntre(inicio, fim, statuses));
    }

    @GetMapping("/vencidas")
    public ResponseEntity<List<MedidaProtetiva>> listarVencidas(@RequestParam LocalDate hoje) {
        List<MedidaProtetiva.StatusMedida> statuses = List.of(
                MedidaProtetiva.StatusMedida.CONCEBIDA,
                MedidaProtetiva.StatusMedida.EM_VIGOR
        );
        return ResponseEntity.ok(repository.findVencidas(hoje, statuses));
    }

    @GetMapping("/numero-processo/{numero}")
    public ResponseEntity<MedidaProtetiva> buscarPorNumeroProcesso(@PathVariable String numero) {
        return repository.findByNumeroProcesso(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedidaProtetiva> atualizar(@PathVariable Long id, @Valid @RequestBody MedidaProtetiva medida) {
        return repository.findById(id)
                .map(existente -> {
                    medida.setId(id);
                    validarRelacionamentos(medida);
                    return ResponseEntity.ok(repository.save(medida));
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

    private void validarRelacionamentos(MedidaProtetiva medida) {
        if (medida.getCaso() != null && medida.getCaso().getId() != null) {
            Caso caso = casoRepository.findById(medida.getCaso().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Caso não encontrado"));
            medida.setCaso(caso);
        }
        if (medida.getVitima() != null && medida.getVitima().getId() != null) {
            Pessoa vitima = pessoaRepository.findById(medida.getVitima().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Vítima não encontrada"));
            medida.setVitima(vitima);
        }
        if (medida.getAgressor() != null && medida.getAgressor().getId() != null) {
            Pessoa agressor = pessoaRepository.findById(medida.getAgressor().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Agressor não encontrado"));
            medida.setAgressor(agressor);
        }
        if (medida.getUsuarioConcedente() != null && medida.getUsuarioConcedente().getId() != null) {
            Usuario concedente = usuarioRepository.findById(medida.getUsuarioConcedente().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário concedente não encontrado"));
            medida.setUsuarioConcedente(concedente);
        }
    }
}