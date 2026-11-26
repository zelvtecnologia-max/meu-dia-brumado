package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "perfis_apoio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilApoio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil_apoio")
    private Integer idPerfilApoio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuaria", nullable = false, unique = true)
    private Usuaria usuaria;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "necessidades_especiais", columnDefinition = "TEXT")
    private String necessidadesEspeciais;

    @Column(name = "rede_apoiio_ativa")
    private Boolean redeApoioAtiva = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void organizarInformacoes() {
        // Método para organizar informações do perfil de apoio
    }

    public void adicionarContato() {
        // Método para adicionar contato ao perfil
    }
}