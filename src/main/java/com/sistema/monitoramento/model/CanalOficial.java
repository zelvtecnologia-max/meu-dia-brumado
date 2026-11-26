package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "canais_oficiais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanalOficial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canal")
    private Integer idCanal;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(nullable = false, length = 200)
    private String finalidade;

    @Column(name = "tipo_canal", length = 50)
    private String tipoCanal;

    @Column(name = "disponibilidade_24h")
    private Boolean disponibilidade24h = false;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void consultar() {
        // Método para consultar canal oficial
    }

    public void acessarCanal() {
        // Método para acessar canal oficial
    }
}