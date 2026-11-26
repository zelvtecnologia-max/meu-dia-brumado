package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 200)
    private String nomeCompleto;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(name = "matricula", unique = true, length = 30)
    private String matricula;

    @Column(name = "cargo_funcao", length = 100)
    private String cargoFuncao;

    @Column(name = "orgao_lotacao", length = 200)
    private String orgaoLotacao;

    @Column(name = "telefone", length = 15)
    private String telefone;

    @Column(name = "celular", length = 15)
    private String celular.

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false, length = 30)
    private PerfilUsuario perfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusUsuario status = StatusUsuario.ATIVO;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "tentativas_login_falhas")
    private Integer tentativasLoginFalhas = 0;

    @Column(name = "bloqueado_ate")
    private LocalDateTime bloqueadoAte;

    @Column(name = "senha_expira_em")
    private LocalDate senhaExpiraEm;

    @Column(name = "deve_trocar_senha")
    private Boolean deveTrocarSenha = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != StatusUsuario.BLOQUEADO && (bloqueadoAte == null || bloqueadoAte.isBefore(LocalDateTime.now()));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return senhaExpiraEm == null || senhaExpiraEm.isAfter(LocalDate.now());
    }

    @Override
    public boolean isEnabled() {
        return status == StatusUsuario.ATIVO;
    }

    public enum PerfilUsuario {
        ADMINISTRADOR,
        GESTOR,
        OPERADOR,
        ANALISTA,
        VISUALIZADOR
    }

    public enum StatusUsuario {
        ATIVO,
        INATIVO,
        BLOQUEADO,
        EXPIRADO
    }
}