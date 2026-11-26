package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "veiculos_relacionados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoRelacionado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @Column(name = "placa", nullable = false, length = 10)
    private String placa;

    @Column(name = "marca", length = 50)
    private String marca;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "cor", length = 30)
    private String cor;

    @Column(name = "ano_fabricacao")
    private Integer anoFabricacao;

    @Column(name = "ano_modelo")
    private Integer anoModelo;

    @Column(name = "tipo_veiculo", length = 30)
    private String tipoVeiculo;

    @Column(name = "renavam", length = 20)
    private String renavam;

    @Column(name = "chassi", length = 50)
    private String chassi;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}