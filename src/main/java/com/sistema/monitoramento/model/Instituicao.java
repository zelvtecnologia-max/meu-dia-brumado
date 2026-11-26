package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "instituicoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instituicao")
    private Integer idInstituicao;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String endereco;

    @Column(length = 15)
    private String telefone;

    @Column(name = "horario_atendimento", length = 100)
    private String horarioAtendimento;

    @Column(length = 150)
    private String email;

    @Column(name = "site_url", length = 500)
    private String siteUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_instituicao", length = 50)
    private TipoInstituicao tipoInstituicao;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServicoAtendimento> servicosAtendimento;

    public void cadastrar() {
        // Método para cadastrar instituição
    }

    public void atualizar() {
        // Método para atualizar instituição
    }

    public void consultar() {
        // Método para consultar instituição
    }

    public enum TipoInstituicao {
        DELEGACIA,
        MINISTERIO_PUBLICO,
        DEFENSORIA_PUBLICA,
        CRAS,
        CREAS,
        CENTRO_REFERENCIA,
        HOSPITAL,
        UBS,
        ONG,
        CONSELHO_TUTELAR,
        OUTRO
    }
}