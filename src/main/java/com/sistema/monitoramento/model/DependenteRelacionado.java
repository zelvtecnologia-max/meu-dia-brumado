package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "dependentes_relacionados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DependenteRelacionado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_responsavel_id", nullable = false)
    private Pessoa pessoaResponsavel;

    @Column(name = "nome_completo", nullable = false, length = 200)
    private String nomeCompleto;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 20)
    private Pessoa.Genero genero;

    @Column(name = "grau_parentesco", nullable = false, length = 30)
    private String grauParentesco;

    @Column(name = "cpf", length = 14)
    private String cpf;

    @Column(name = "estuda")
    private Boolean estuda;

    @Column(name = "escola", length = 200)
    private String escola;

    @Column(name = "possui_deficiencia")
    private Boolean possuiDeficiencia;

    @Column(name = "tipo_deficiencia", length = 100)
    private String tipoDeficiencia;

    @Column(name = "uso_medicacao_continua")
    private Boolean usoMedicacaoContinua;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}