package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuaria")
    private Integer idUsuaria;

    @Column(nullable = false, length = 200)
    private String nomeCompleto;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    private String senhaHash;

    @Column(name = "telefone", length = 15)
    private String telefone;

    @Column(name = "celular", length = 15)
    private String celular;

    @Column(columnDefinition = "TEXT")
    private String endereco;

    @Column(name = "data_nascimento")
    private LocalDateTime dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusUsuaria status = StatusUsuaria.ATIVA;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "usuaria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PerfilApoio perfilApoio;

    @OneToMany(mappedBy = "usuaria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InformacaoSeguranca> informacoesSeguranca;

    @OneToMany(mappedBy = "usuaria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContatoConfianca> contatosConfianca;

    public void consultarInformacoes() {
        // Método para consultar informações da usuária
    }

    public void gerenciarPerfilApoio() {
        // Método para gerenciar perfil de apoio
    }

    public enum StatusUsuaria {
        ATIVA,
        INATIVA,
        BLOQUEADA,
        SUSPENSA
    }
}