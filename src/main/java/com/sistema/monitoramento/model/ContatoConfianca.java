package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contatos_confianca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoConfianca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato")
    private Integer idContato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuaria", nullable = false)
    private Usuaria usuaria;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, length = 15)
    private String telefone;

    @Column(length = 150)
    private String email;

    @Column(name = "tipo_relacao", length = 50)
    private String tipoRelacao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "contato_principal")
    private Boolean contatoPrincipal = false;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void adicionar() {
        // Método para adicionar contato de confiança
    }

    public void atualizar() {
        // Método para atualizar contato de confiança
    }

    public void remover() {
        // Método para remover contato de confiança
        this.ativo = false;
    }
}