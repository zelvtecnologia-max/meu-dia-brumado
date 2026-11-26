package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "informacoes_saude_relevante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacaoSaudeRelevante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @Column(name = "tipo_informacao", nullable = false, length = 50)
    private String tipoInformacao;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "medicacao", length = 500)
    private String medicacao;

    @Column(name = "profissional_responsavel", length = 200)
    private String profissionalResponsavel;

    @Column(name = "unidade_saude", length = 200)
    private String unidadeSaude;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes.

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}