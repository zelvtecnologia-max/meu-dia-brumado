package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.*;
import com.sistema.monitoramento.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final CasoRepository casoRepository;
    private final MedidaProtetivaRepository medidaRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final PessoaRepository pessoaRepository;
    private final RondaRepository rondaRepository;
    private final AlertaRepository alertaRepository;
    private final FatorRiscoRepository fatorRiscoRepository;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalCasos", casoRepository.count());
        dashboard.put("casosAbertos", casoRepository.countByStatus(Caso.StatusCaso.ABERTO));
        dashboard.put("casosEmAndamento", casoRepository.countByStatus(Caso.StatusCaso.EM_ANDAMENTO));
        dashboard.put("casosEncerrados", casoRepository.countByStatus(Caso.StatusCaso.ENCERRADO));
        dashboard.put("totalMedidas", medidaRepository.count());
        dashboard.put("medidasEmVigor", medidaRepository.countByStatus(MedidaProtetiva.StatusMedida.EM_VIGOR));
        dashboard.put("medidasVencidas", medidaRepository.countByStatus(MedidaProtetiva.StatusMedida.VENCIDA));
        dashboard.put("totalOcorrencias", ocorrenciaRepository.count());
        dashboard.put("ocorrenciasMes", ocorrenciaRepository.findByDataOcorrenciaBetween(
                LocalDate.now().withDayOfMonth(1), LocalDate.now()).size());
        dashboard.put("totalPessoas", pessoaRepository.count());
        dashboard.put("totalUsuarios", usuarioRepository.count());
        dashboard.put("alertasNaoLidos", alertaRepository.countByUsuarioDestinatarioAndStatus(
                usuarioRepository.findAll().get(0), Alerta.StatusAlerta.NAO_LIDO));
        dashboard.put("rondasHoje", rondaRepository.findAgendadasParaHoje(LocalDate.now(),
                List.of(Ronda.StatusRonda.PLANEJADA, Ronda.StatusRonda.EM_ANDAMENTO)).size());
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/casos-por-status")
    public ResponseEntity<Map<String, Long>> casosPorStatus() {
        Map<String, Long> resultado = new HashMap<>();
        for (Caso.StatusCaso status : Caso.StatusCaso.values()) {
            resultado.put(status.name(), casoRepository.countByStatus(status));
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/medidas-por-status")
    public ResponseEntity<Map<String, Long>> medidasPorStatus() {
        Map<String, Long> resultado = new HashMap<>();
        for (MedidaProtetiva.StatusMedida status : MedidaProtetiva.StatusMedida.values()) {
            resultado.put(status.name(), medidaRepository.countByStatus(status));
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/ocorrencias-por-gravidade")
    public ResponseEntity<Map<String, Long>> ocorrenciasPorGravidade() {
        Map<String, Long> resultado = new HashMap<>();
        for (Ocorrencia.Gravidade gravidade : Ocorrencia.Gravidade.values()) {
            resultado.put(gravidade.name(), (long) ocorrenciaRepository.findByGravidade(gravidade).size());
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/casos-por-grau-risco")
    public ResponseEntity<Map<String, Long>> casosPorGrauRisco() {
        Map<String, Long> resultado = new HashMap<>();
        for (Caso.GrauRisco grau : Caso.GrauRisco.values()) {
            resultado.put(grau.name(), (long) casoRepository.findByGrauRisco(grau).size());
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/fatores-risco-por-nivel")
    public ResponseEntity<Map<String, Long>> fatoresRiscoPorNivel() {
        Map<String, Long> resultado = new HashMap<>();
        for (FatorRisco.NivelRisco nivel : FatorRisco.NivelRisco.values()) {
            resultado.put(nivel.name(), (long) fatorRiscoRepository.findByNivel(nivel).size());
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/rondas-por-status")
    public ResponseEntity<Map<String, Long>> rondasPorStatus() {
        Map<String, Long> resultado = new HashMap<>();
        for (Ronda.StatusRonda status : Ronda.StatusRonda.values()) {
            resultado.put(status.name(), rondaRepository.countByStatus(status));
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/alertas-por-tipo")
    public ResponseEntity<Map<String, Long>> alertasPorTipo() {
        Map<String, Long> resultado = new HashMap<>();
        for (Alerta.TipoAlerta tipo : Alerta.TipoAlerta.values()) {
            resultado.put(tipo.name(), (long) alertaRepository.findByTipoAlerta(tipo).size());
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/pessoas-por-tipo")
    public ResponseEntity<Map<String, Long>> pessoasPorTipo() {
        Map<String, Long> resultado = new HashMap<>();
        for (Pessoa.TipoPessoa tipo : Pessoa.TipoPessoa.values()) {
            resultado.put(tipo.name(), (long) pessoaRepository.findByTipoPessoa(tipo).size());
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/evolucao-casos")
    public ResponseEntity<List<Map<String, Object>>> evolucaoCasos(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        List<Caso> casos = casoRepository.findByDataAberturaBetween(inicio, fim);
        Map<LocalDate, Long> contagem = new TreeMap<>();
        for (Caso caso : casos) {
            contagem.merge(caso.getDataAbertura(), 1L, Long::sum);
        }
        List<Map<String, Object>> resultado = new ArrayList<>();
        contagem.forEach((data, count) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("data", data);
            item.put("quantidade", count);
            resultado.add(item);
        });
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/casos-vitima/{vitimaId}")
    public ResponseEntity<Page<Caso>> casosDaVitima(@PathVariable Long vitimaId, Pageable pageable) {
        return pessoaRepository.findById(vitimaId)
                .map(vitima -> ResponseEntity.ok(casoRepository.findByVitima(vitima, pageable)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/medidas-vencendo")
    public ResponseEntity<List<MedidaProtetiva>> medidasVencendo(
            @RequestParam(defaultValue = "30") int dias) {
        LocalDate hoje = LocalDate.now();
        LocalDate fim = hoje.plusDays(dias);
        List<MedidaProtetiva.StatusMedida> statuses = List.of(
                MedidaProtetiva.StatusMedida.CONCEBIDA,
                MedidaProtetiva.StatusMedida.EM_VIGOR
        );
        return ResponseEntity.ok(medidaRepository.findVencendoEntre(hoje, fim, statuses));
    }

    @GetMapping("/rondas-periodo")
    public ResponseEntity<Page<Ronda>> rondasPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            Pageable pageable) {
        return ResponseEntity.ok(rondaRepository.findByDataRondaBetween(inicio, fim, pageable));
    }
}