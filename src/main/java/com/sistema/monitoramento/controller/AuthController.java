package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Administrador;
import com.sistema.monitoramento.model.UsuarioAdministrativo;
import com.sistema.monitoramento.repository.AdministradorRepository;
import com.sistema.monitoramento.repository.UsuarioAdministrativoRepository;
import com.sistema.monitoramento.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioAdministrativoRepository usuarioRepository;
    private final AdministradorRepository administradorRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.senha())
            );

            UsuarioAdministrativo usuario = (UsuarioAdministrativo) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(usuario);

            return ResponseEntity.ok(new LoginResponse(token, usuario.getIdUsuario(), usuario.getNome(), usuario.getEmail()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", "Credenciais inválidas"));
        }
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", "Email já cadastrado"));
        }

        Administrador admin = new Administrador();
        admin.setNome(request.nome());
        admin.setEmail(request.email());
        admin.setSenhaHash(passwordEncoder.encode(request.senha()));
        admin.setAtivo(true);
        admin.setNivelAcesso("ADMIN");
        admin.setPodeGerenciarUsuarios(true);
        admin.setPodeGerenciarConteudos(true);
        admin.setPodeGerenciarConfiguracoes(true);
        admin.setPodeConsultarAuditoria(true);
        admin.setPodeConsultarRelatorios(true);

        admin = administradorRepository.save(admin);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id", admin.getIdUsuario(),
                "nome", admin.getNome(),
                "email", admin.getEmail()
        ));
    }

    public record LoginRequest(String email, String senha) {}
    public record LoginResponse(String token, Integer id, String nome, String email) {}
    public record RegisterRequest(String nome, String email, String senha) {}
}