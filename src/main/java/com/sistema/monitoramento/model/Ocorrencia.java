package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "ocorrencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_ocorrencia", nullable = false, unique = true, length = 50)
    private String numeroOcorrencia;

    @Column(name = "tipo_ocorrencia", nullable = false, length = 50)
    private String tipoOcorrencia;

    @Column(name = "data_ocorrencia", nullable = false)
    private LocalDate dataOcorrencia;

    @Column(name = "hora_ocorrencia")
    private LocalTime horaOcorrencia;

    @Column(name = "local_ocorrencia", length = 500)
    private String localOcorrencia;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusOcorrencia status;

    @Enumerated(EnumType.STRING)
    @Column(name = "gravidade", length = 20)
    private Gravidade gravidade;

    @Column(name = "medidas_tomadas", columnDefinition = "TEXT")
    private String medidasTomadas;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caso_id")
    private Caso caso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medida_protetiva_id")
    private MedidaProtetiva medidaProtetiva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vitima_id")
    private Pessoa vitima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agressor_id")
    private Pessoa agressor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_registro_id")
    private Usuario usuarioRegistro;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum StatusOcorrencia {
        REGISTRADA,
        EM_INVESTIGACAO,
        CONCLUIDA,
        ARQUIVADA,
        ENCAMINHADA_MP
    }

    public enum Gravidade {
        LEVE,
        MEDIA,
        GRAVE,
        GRAVISSIMA
    }
}