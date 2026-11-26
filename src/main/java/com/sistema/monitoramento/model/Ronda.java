package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "rondas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ronda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_ronda", nullable = false, unique = true, length = 50)
    private String codigoRonda;

    @Column(name = "data_ronda", nullable = false)
    private LocalDate dataRonda;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim")
    private LocalTime horaFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ronda", length = 30)
    private TipoRonda tipoRonda;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusRonda status;

    @Column(name = "rota", columnDefinition = "TEXT")
    private String rota;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medida_protetiva_id")
    private MedidaProtetiva medidaProtetiva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_responsavel_id")
    private Usuario usuarioResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_executor_id")
    private Usuario usuarioExecutor;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum TipoRonda {
        PREVENTIVA,
        FISCALIZACAO,
        APOIO_VITIMA,
        VERIFICACAO_MEDIDA,
        OUTRA
    }

    public enum StatusRonda {
        PLANEJADA,
        EM_ANDAMENTO,
        CONCLUIDA,
        CANCELADA,
        REAGENDADA
    }
}