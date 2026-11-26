package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "dados_complementares_agressor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DadosComplementaresAgressor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false, unique = true)
    private Pessoa pessoa;

    @Column(name = "profissao", length = 100)
    private String profissao;

    @Column(name = "local_trabalho", length = 200)
    private String localTrabalho;

    @Column(name = "renda_mensal")
    private Double rendaMensal;

    @Column(name = "escolaridade", length = 50)
    private String escolaridade;

    @Column(name = "possui_porte_arma")
    private Boolean possuiPorteArma;

    @Column(name = "numero_porte_arma", length = 50)
    private String numeroPorteArma;

    @Column(name = "tipo_arma", length = 50)
    private String tipoArma;

    @Column(name = "antecedentes_criminais")
    private Boolean antecedentesCriminais;

    @Column(name = "descricao_antecedentes", columnDefinition = "TEXT")
    private String descricaoAntecedentes;

    @Column(name = "medida_cautelar_ativa")
    private Boolean medidaCautelarAtiva;

    @Column(name = "descricao_medida_cautelar", columnDefinition = "TEXT")
    private String descricaoMedidaCautelar;

    @Column(name = "uso_substancias")
    private Boolean usoSubstancias;

    @Column(name = "tipo_substancia", length = 100)
    private String tipoSubstancia;

    @Column(name = "tratamento_saude_mental")
    private Boolean tratamentoSaudeMental;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}