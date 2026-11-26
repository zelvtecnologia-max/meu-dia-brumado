package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "conteudos_educativos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConteudoEducativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conteudo")
    private Integer idConteudo;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String texto;

    @Column(length = 100)
    private String categoria;

    @Column(length = 200)
    private String fonte;

    @Column(name = "data_revisao")
    private LocalDate dataRevisao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private StatusConteudo status = StatusConteudo.RASCUNHO;

    @Column(name = "publico_alvo", columnDefinition = "TEXT")
    private String publicoAlvo;

    @Column(name = "tags", columnDefinition = "TEXT")
    private String tags;

    @Column(name = "visualizacoes")
    private Integer visualizacoes = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void cadastrar() {
        // Método para cadastrar conteúdo educativo
    }

    public void atualizar() {
        // Método para atualizar conteúdo educativo
    }

    public void consultar() {
        // Método para consultar conteúdo educativo
    }

    public enum StatusConteudo {
        RASCUNHO,
        PUBLICADO,
        ARQUIVADO,
        EM_REVISAO
    }
}