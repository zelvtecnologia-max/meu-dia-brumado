package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "relatos_agressao_anterior")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatoAgressaoAnterior {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caso_id", nullable = false)
    private Caso caso;

    @Column(name = "data_ocorrencia", nullable = false)
    private LocalDate dataOcorrencia;

    @Column(name = "local_ocorrencia", length = 500)
    private String localOcorrencia;

    @Column(name = "tipo_agressao", length = 50)
    private String tipoAgressao;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "houve_lesao")
    private Boolean houveLesao;

    @Column(name = "tipo_lesao", length = 100)
    private String tipoLesao;

    @Column(name = "houve_boletim_ocorrencia")
    private Boolean houveBoletimOcorrencia;

    @Column(name = "numero_boletim", length = 50)
    private String numeroBoletim;

    @Column(name = "medidas_tomadas", columnDefinition = "TEXT")
    private String medidasTomadas;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}