package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "testemunhas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Testemunha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ocorrencia_id")
    private Ocorrencia ocorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caso_id")
    private Caso caso;

    @Column(name = "tipo_depoimento", length = 30)
    private String tipoDepoimento;

    @Column(columnDefinition = "TEXT")
    private String depoimento;

    @Column(name = "data_depoimento")
    private LocalDate dataDepoimento;

    @Column(name = "local_depoimento", length = 500)
    private String localDepoimento;

    @Column(name = "autoridade_ouvinte", length = 200)
    private String autoridadeOuvinte;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}