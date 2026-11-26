package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "profissionais_autorizados")
@DiscriminatorValue("PROFISSIONAL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfissionalAutorizado extends UsuarioAdministrativo {

    @Column(name = "registro_profissional", length = 50)
    private String registroProfissional;

    @Column(name = "orgao_emissor", length = 100)
    private String orgaoEmissor;

    @Column(name = "especialidade", length = 100)
    private String especialidade;

    @Column(name = "cargo_funcao", length = 100)
    private String cargoFuncao;

    @Column(name = "orgao_lotacao", length = 200)
    private String orgaoLotacao;

    @Column(name = "telefone", length = 15)
    private String telefone;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "profissional_funcoes_permitidas",
        joinColumns = @JoinColumn(name = "id_profissional"),
        inverseJoinColumns = @JoinColumn(name = "id_funcao")
    )
    private List<FuncaoPermitida> funcoesPermitidas;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void consultarFuncoesPermitidas() {
        // Método para consultar funções permitidas
    }
}