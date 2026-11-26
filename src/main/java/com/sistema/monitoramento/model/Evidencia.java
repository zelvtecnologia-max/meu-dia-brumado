package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_evidencia", nullable = false, unique = true, length = 50)
    private String codigoEvidencia;

    @Column(name = "tipo_evidencia", nullable = false, length = 50)
    private String tipoEvidencia;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "arquivo_url", length = 500)
    private String arquivoUrl;

    @Column(name = "hash_arquivo", length = 100)
    private String hashArquivo;

    @Column(name = "data_coleta", nullable = false)
    private LocalDate dataColeta;

    @Column(name = "local_coleta", length = 500)
    private String localColeta;

    @Column(name = "cadeia_custodia", columnDefinition = "TEXT")
    private String cadeiaCustodia;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusEvidencia status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ocorrencia_id")
    private Ocorrencia ocorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caso_id")
    private Caso caso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_coletor_id")
    private Usuario usuarioColetor;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum StatusEvidencia {
        COLETADA,
        ANALISANDO,
        ARMAZENADA,
        PERDIDA,
        INUTILIZADA,
        DEVOLVIDA
    }
}