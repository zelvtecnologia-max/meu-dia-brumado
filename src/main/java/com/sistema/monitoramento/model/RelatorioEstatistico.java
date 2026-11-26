package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "relatorios_estatisticos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioEstatistico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relatorio")
    private Integer idRelatorio;

    @Column(nullable = false, length = 50)
    private String periodo;

    @Column(name = "data_geracao", nullable = false)
    private LocalDate dataGeracao;

    @Column(name = "indicadores_anonimizados", columnDefinition = "TEXT")
    private String indicadoresAnonimizados;

    @Column(name = "tipo_relatorio", length = 50)
    private String tipoRelatorio;

    @Column(name = "formato_arquivo", length = 20)
    private String formatoArquivo;

    @Column(name = "caminho_arquivo", length = 500)
    private String caminhoArquivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private StatusRelatorio status = StatusRelatorio.GERADO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gerado_por")
    private Administrador geradoPor;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void gerar() {
        // Método para gerar relatório estatístico
        this.dataGeracao = LocalDate.now();
        this.status = StatusRelatorio.GERADO;
    }

    public void consultar() {
        // Método para consultar relatório estatístico
    }

    public enum StatusRelatorio {
        GERANDO,
        GERADO,
        ERRO,
        ARQUIVADO
    }
}