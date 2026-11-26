package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "servicos_atendimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicoAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico")
    private Integer idServico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instituicao", nullable = false)
    private Instituicao instituicao;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "formas_atendimento", columnDefinition = "TEXT")
    private String formasAtendimento;

    @Column(length = 100)
    private String contato;

    @Column(name = "horario_funcionamento", length = 100)
    private String horarioFuncionamento;

    @Column(name = "publico_alvo", columnDefinition = "TEXT")
    private String publicoAlvo;

    @Column(name = "requisitos_acesso", columnDefinition = "TEXT")
    private String requisitosAcesso;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void cadastrar() {
        // Método para cadastrar serviço de atendimento
    }

    public void atualizar() {
        // Método para atualizar serviço de atendimento
    }

    public void consultar() {
        // Método para consultar serviço de atendimento
    }
}