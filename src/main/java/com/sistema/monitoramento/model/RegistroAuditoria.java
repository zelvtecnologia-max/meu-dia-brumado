package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "registros_auditoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Integer idRegistro;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "tipo_usuario", length = 50)
    private String tipoUsuario;

    @Column(name = "acao", nullable = false, length = 100)
    private String acao;

    @Column(name = "entidade_afetada", length = 100)
    private String entidadeAfetada;

    @Column(name = "id_entidade")
    private Integer idEntidade;

    @Column(name = "dados_anteriores", columnDefinition = "TEXT")
    private String dadosAnteriores;

    @Column(name = "dados_novos", columnDefinition = "TEXT")
    private String dadosNovos;

    @Column(name = "endereco_ip", length = 45)
    private String enderecoIp;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "sucesso")
    private Boolean sucesso = true;

    @Column(name = "mensagem_erro", columnDefinition = "TEXT")
    private String mensagemErro;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void registrar() {
        // Método para registrar auditoria
        this.dataHora = LocalDateTime.now();
    }

    public void consultar() {
        // Método para consultar registro de auditoria
    }
}