package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medidas_protetivas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedidaProtetiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_processo", nullable = false, unique = true, length = 50)
    private String numeroProcesso;

    @Column(name = "numero_medida", length = 50)
    private String numeroMedida;

    @Column(name = "tipo_medida", nullable = false, length = 100)
    private String tipoMedida;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_concessao", nullable = false)
    private LocalDate dataConcessao;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "data_cumprimento")
    private LocalDate dataCumprimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusMedida status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_urgencia", length = 20)
    private TipoUrgencia tipoUrgencia;

    @Column(name = "distancia_minima")
    private Integer distanciaMinima;

    @Column(name = "locais_proibidos", columnDefinition = "TEXT")
    private String locaisProibidos;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caso_id", nullable = false)
    private Caso caso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vitima_id", nullable = false)
    private Pessoa vitima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agressor_id", nullable = false)
    private Pessoa agressor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_concedente_id")
    private Usuario usuarioConcedente;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum StatusMedida {
        CONCEBIDA,
        EM_VIGOR,
        CUMPRIDA,
        VENCIDA,
        REVOGADA,
        SUSPENSA
    }

    public enum TipoUrgencia {
        URGENTE,
        NORMAL,
        PREVENTIVA
    }
}