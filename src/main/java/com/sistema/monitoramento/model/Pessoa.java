package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pessoas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nomeCompleto;

    @Column(name = "nome_social", length = 200)
    private String nomeSocial;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 20)
    private Genero genero;

    @Column(name = "nome_mae", length = 200)
    private String nomeMae;

    @Column(name = "nome_pai", length = 200)
    private String nomePai;

    @Column(length = 15)
    private String telefone;

    @Column(length = 15)
    private String celular;

    @Column(length = 100)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String endereco;

    @Column(name = "numero_endereco", length = 20)
    private String numeroEndereco;

    @Column(name = "complemento_endereco", length = 100)
    private String complementoEndereco;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    @Column(name = "cep", length = 9)
    private String cep;

    @Column(name = "naturalidade_cidade", length = 100)
    private String naturalidadeCidade;

    @Column(name = "naturalidade_estado", length = 2)
    private String naturalidadeEstado;

    @Column(name = "nacionalidade", length = 50)
    private String nacionalidade;

    @Column(name = "rg", length = 20)
    private String rg;

    @Column(name = "orgao_emissor_rg", length = 20)
    private String orgaoEmissorRg;

    @Column(name = "uf_rg", length = 2)
    private String ufRg;

    @Column(name = "data_emissao_rg")
    private LocalDate dataEmissaoRg;

    @Column(name = "foto_url", length = 500)
    private String fotoUrl;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", nullable = false, length = 20)
    private TipoPessoa tipoPessoa;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum Genero {
        MASCULINO,
        FEMININO,
        OUTRO,
        NAO_INFORMADO
    }

    public enum TipoPessoa {
        VITIMA,
        AGRESSOR,
        TESTEMUNHA,
        FAMILIAR,
        OUTRO
    }
}