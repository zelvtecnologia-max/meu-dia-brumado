package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "casos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Caso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_caso", nullable = false, unique = true, length = 50)
    private String numeroCaso;

    @Column(name = "tipo_caso", nullable = false, length = 50)
    private String tipoCaso;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_abertura", nullable = false)
    private LocalDate dataAbertura;

    @Column(name = "data_encerramento")
    private LocalDate dataEncerramento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusCaso status;

    @Enumerated(EnumType.STRING)
    @Column(name = "grau_risco", length = 20)
    private GrauRisco grauRisco;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vitima_id", nullable = false)
    private Pessoa vitima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agressor_principal_id")
    private Pessoa agressorPrincipal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum StatusCaso {
        ABERTO,
        EM_ANDAMENTO,
        SUSPENSO,
        ENCERRADO,
        ARQUIVADO
    }

    public enum GrauRisco {
        BAIXO,
        MEDIO,
        ALTO,
        CRITICO
    }
}