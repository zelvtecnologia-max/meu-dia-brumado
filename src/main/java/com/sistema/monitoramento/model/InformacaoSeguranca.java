package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "informacoes_seguranca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacaoSeguranca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informacao")
    private Integer idInformacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuaria", nullable = false)
    private Usuaria usuaria;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", length = 50)
    private CategoriaInformacao categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_risco", length = 20)
    private NivelRisco nivelRisco;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void registrar() {
        // Método para registrar informação de segurança
    }

    public void atualizar() {
        // Método para atualizar informação de segurança
    }

    public void consultar() {
        // Método para consultar informação de segurança
    }

    public enum CategoriaInformacao {
        MEDIDA_PROTETIVA,
        PLANO_SEGURANCA,
        CONTATO_EMERGENCIA,
        LOCAL_SEGURO,
        DOCUMENTO_IMPORTANTE,
        OUTRO
    }

    public enum NivelRisco {
        BAIXO,
        MEDIO,
        ALTO,
        CRITICO
    }
}