package com.sistema.monitoramento.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios_administrativos")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class UsuarioAdministrativo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "tentativas_login_falhas")
    private Integer tentativasLoginFalhas = 0;

    @Column(name = "bloqueado_ate")
    private LocalDateTime bloqueadoAte;

    @Column(name = "senha_expira_em")
    private LocalDateTime senhaExpiraEm;

    @Column(name = "deve_trocar_senha")
    private Boolean deveTrocarSenha = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_perfis_acesso",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_perfil")
    )
    private List<PerfilAcesso> perfisAcesso;

    public Boolean autenticar() {
        // Método para autenticar usuário administrativo
        return this.ativo &&
               (this.bloqueadoAte == null || this.bloqueadoAte.isBefore(LocalDateTime.now())) &&
               (this.senhaExpiraEm == null || this.senhaExpiraEm.isAfter(LocalDateTime.now()));
    }

    public void encerrarSessao() {
        // Método para encerrar sessão
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfisAcesso.stream()
            .flatMap(perfil -> perfil.getPermissoes().stream())
            .map(permissao -> new SimpleGrantedAuthority(permissao.getNome()))
            .toList();
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return senhaExpiraEm == null || senhaExpiraEm.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return ativo && (bloqueadoAte == null || bloqueadoAte.isBefore(LocalDateTime.now()));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return senhaExpiraEm == null || senhaExpiraEm.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
}