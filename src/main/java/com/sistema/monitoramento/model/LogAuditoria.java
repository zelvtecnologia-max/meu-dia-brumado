package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs_auditoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "acao", nullable = false, length = 50)
    private String acao;

    @Column(name = "entidade", nullable = false, length = 50)
    private String entidade;

    @Column(name = "entidade_id")
    private Long entidadeId;

    @Column(name = "dados_anteriores", columnDefinition = "TEXT")
    private String dadosAnteriores;

    @Column(name = "dados_novos", columnDefinition = "TEXT")
    private String dadosNovos;

    @Column(name = "ip_origem", length = 45)
    private String ipOrigem;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "endpoint", length = 200)
    private String endpoint;

    @Column(name = "metodo_http", length = 10)
    private String metodoHttp;

    @Column(name = "status_resposta")
    private Integer statusResposta;

    @Column(name = "tempo_execucao_ms")
    private Long tempoExecucaoMs;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}