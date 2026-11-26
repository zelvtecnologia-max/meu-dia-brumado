package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "configuracoes_plataforma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracaoPlataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuracao")
    private Integer idConfiguracao;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String valor;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "tipo_dado", length = 50)
    private String tipoDado;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "editavel")
    private Boolean editavel = true;

    @Column(name = "sensivel")
    private Boolean sensivel = false;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atualizado_por")
    private Administrador atualizadoPor;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void atualizar() {
        // Método para atualizar configuração
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void consultar() {
        // Método para consultar configuração
    }
}