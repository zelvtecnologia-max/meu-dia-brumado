package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "administradores")
@DiscriminatorValue("ADMINISTRADOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Administrador extends UsuarioAdministrativo {

    @Column(name = "nivel_acesso", length = 50)
    private String nivelAcesso;

    @Column(name = "pode_gerenciar_usuarios")
    private Boolean podeGerenciarUsuarios = true;

    @Column(name = "pode_gerenciar_conteudos")
    private Boolean podeGerenciarConteudos = true;

    @Column(name = "pode_gerenciar_configuracoes")
    private Boolean podeGerenciarConfiguracoes = true;

    @Column(name = "pode_consultar_auditoria")
    private Boolean podeConsultarAuditoria = true;

    @Column(name = "pode_consultar_relatorios")
    private Boolean podeConsultarRelatorios = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void gerenciarConteudos() {
        // Método para gerenciar conteúdos
    }

    public void gerenciarDiretorio() {
        // Método para gerenciar diretório
    }

    public void gerenciarUsuarios() {
        // Método para gerenciar usuários
    }

    public void consultarRelatorios() {
        // Método para consultar relatórios
    }

    public void consultarAuditoria() {
        // Método para consultar auditoria
    }

    public void gerenciarConfiguracoes() {
        // Método para gerenciar configurações
    }
}