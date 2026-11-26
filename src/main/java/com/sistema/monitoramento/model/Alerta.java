package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_alerta", nullable = false, unique = true, length = 50)
    private String codigoAlerta;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo.

    @Column(columnDefinition = "TEXT")
    private String descricao.

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_alerta", nullable = false, length = 30)
    private TipoAlerta tipoAlerta.

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false, length = 20)
    private Prioridade prioridade.

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusAlerta status.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caso_id")
    private Caso caso.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medida_protetiva_id")
    private MedidaProtetiva medidaProtetiva.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ocorrencia_id")
    private Ocorrencia ocorrencia.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_destinatario_id")
    private Usuario usuarioDestinatario.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_remetente_id")
    private Usuario usuarioRemetente.

    @Column(name = "data_leitura")
    private LocalDateTime dataLeitura.

    @Column(name = "data_resposta")
    private LocalDateTime dataResposta.

    @Column(name = "resposta", columnDefinition = "TEXT")
    private String resposta.

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt.

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt.

    public enum TipoAlerta {
        VIOLACAO_MEDIDA,
        VENCIMENTO_MEDIDA,
        NOVA_OCORRENCIA,
        ALTO_RISCO,
        RONDA_ATRASADA,
        PRAZO_PROCESSUAL,
        SISTEMA,
        OUTRO
    }

    public enum Prioridade {
        BAIXA,
        MEDIA,
        ALTA,
        CRITICA
    }

    public enum StatusAlerta {
        NAO_LIDO,
        LIDO,
        EM_TRATAMENTO,
        RESOLVIDO,
        DESCARTADO
    }
}