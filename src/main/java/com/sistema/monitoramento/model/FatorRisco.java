package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "fatores_risco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FatorRisco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caso_id", nullable = false)
    private Caso caso;

    @Column(name = "codigo_fator", nullable = false, length = 20)
    private String codigoFator;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", length = 30)
    private CategoriaFator categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel", length = 20)
    private NivelRisco nivel;

    @Column(name = "data_identificacao", nullable = false)
    private LocalDate dataIdentificacao;

    @Column(name = "data_reavaliacao")
    private LocalDate dataReavaliacao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum CategoriaFator {
        PESSOAL,
        FAMILIAR,
        SOCIAL,
        ECONOMICO,
        PSICOLOGICO,
        COMPORTAMENTAL
    }

    public enum NivelRisco {
        BAIXO,
        MEDIO,
        ALTO,
        CRITICO
    }
}