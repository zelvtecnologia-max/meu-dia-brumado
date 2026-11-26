package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "testemunhos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Testemunho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_testemunho")
    private Integer idTestemunho;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusTestemunho status = StatusTestemunho.PENDENTE;

    @Column(name = "autor_anonimo")
    private Boolean autorAnonimo = true;

    @Column(name = "aprovado_por", length = 100)
    private String aprovadoPor;

    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void publicar() {
        // Método para publicar testemunho
        this.status = StatusTestemunho.PUBLICADO;
        this.dataPublicacao = LocalDate.now();
    }

    public void consultar() {
        // Método para consultar testemunho
    }

    public enum StatusTestemunho {
        PENDENTE,
        PUBLICADO,
        REJEITADO,
        ARQUIVADO
    }
}