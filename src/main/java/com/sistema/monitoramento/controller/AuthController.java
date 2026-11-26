package com.sistema.monitoramento.controller;

import com.sistema.monitoramento.model.Usuario;
import com.sistema.monitoramento.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String JWT_SECRET = "sistema-monitoramento-jwt-secret-key-2024-super-seguro";
    private static final long JWT_EXPIRATION = 86400000; // 24 horas

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getSenha())
            );

            Usuario usuario = (Usuario) authentication.getPrincipal();

            // Atualizar último login
            usuario.setUltimoLogin(LocalDateTime.now());
            usuario.setTentativasLoginFalhas(0);
            usuarioRepository.save(usuario);

            String token = gerarToken(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tipo", "Bearer");
            response.put("expiraEm", JWT_EXPIRATION);
            response.put("usuario", mapUsuario(usuario));

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            // Incrementar tentativas falhas
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
            usuarioOpt.ifPresent(u -> {
                u.setTentativasLoginFalhas(u.getTentativasLoginFalhas() + 1);
                if (u.getTentativasLoginFalhas() >= 5) {
                    u.setStatus(Usuario.StatusUsuario.BLOQUEADO);
                    u.setBloqueadoAte(LocalDateTime.now().plusMinutes(30));
                }
                usuarioRepository.save(u);
            });

            Map<String, String> error = new HashMap<>();
            error.put("erro", "Credenciais inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistrarRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            Map<String, String> error = new HashMap<>();
            error.put("erro", "Usuário já existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            Map<String, String> error = new HashMap<>();
            error.put("erro", "Email já cadastrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        if (request.getMatricula() != null && usuarioRepository.existsByMatricula(request.getMatricula())) {
            Map<String, String> error = new HashMap<>();
            error.put("erro", "Matrícula já cadastrada");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setNomeCompleto(request.getNomeCompleto());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setMatricula(request.getMatricula());
        usuario.setCargoFuncao(request.getCargoFuncao());
        usuario.setOrgaoLotacao(request.getOrgaoLotacao());
        usuario.setTelefone(request.getTelefone());
        usuario.setCelular(request.getCelular());
        usuario.setPerfil(request.getPerfil() != null ? request.getPerfil() : Usuario.PerfilUsuario.OPERADOR);
        usuario.setStatus(Usuario.StatusUsuario.ATIVO);
        usuario.setDeveTrocarSenha(true);

        Usuario salvo = usuarioRepository.save(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Usuário criado com sucesso");
        response.put("usuario", mapUsuario(salvo));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        try {
            String username = extrairUsername(token);
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Usuario usuario = usuarioOpt.get();
            if (usuario.getStatus() != Usuario.StatusUsuario.ATIVO) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String novoToken = gerarToken(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("token", novoToken);
            response.put("tipo", "Bearer");
            response.put("expiraEm", JWT_EXPIRATION);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Em JWT stateless, logout é feito no cliente removendo o token
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Logout realizado com sucesso");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        try {
            String username = extrairUsername(token);
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.ok(mapUsuario(usuarioOpt.get()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/alterar-senha")
    public ResponseEntity<?> alterarSenha(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody AlterarSenhaRequest request) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        try {
            String username = extrairUsername(token);
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Usuario usuario = usuarioOpt.get();

            if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())) {
                Map<String, String> error = new HashMap<>();
                error.put("erro", "Senha atual incorreta");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
            usuario.setDeveTrocarSenha(false);
            usuario.setSenhaExpiraEm(LocalDateTime.now().plusDays(90).toLocalDate());
            usuarioRepository.save(usuario);

            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Senha alterada com sucesso");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("id", usuario.getId())
                .claim("perfil", usuario.getPerfil().name())
                .claim("nome", usuario.getNomeCompleto())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    private String extrairUsername(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private Map<String, Object> mapUsuario(Usuario usuario) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", usuario.getId());
        map.put("username", usuario.getUsername());
        map.put("nomeCompleto", usuario.getNomeCompleto());
        map.put("email", usuario.getEmail());
        map.put("matricula", usuario.getMatricula());
        map.put("cargoFuncao", usuario.getCargoFuncao());
        map.put("orgaoLotacao", usuario.getOrgaoLotacao());
        map.put("perfil", usuario.getPerfil());
        map.put("status", usuario.getStatus());
        map.put("ultimoLogin", usuario.getUltimoLogin());
        map.put("deveTrocarSenha", usuario.getDeveTrocarSenha());
        return map;
    }

    @Data
    public static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String senha;
    }

    @Data
    public static class RegistrarRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String nomeCompleto;
        @NotBlank
        private String email;
        @NotBlank
        private String senha;
        private String matricula;
        private String cargoFuncao;
        private String orgaoLotacao;
        private String telefone;
        private String celular;
        private Usuario.PerfilUsuario perfil;
    }

    @Data
    public static class AlterarSenhaRequest {
        @NotBlank
        private String senhaAtual;
        @NotBlank
        private String novaSenha;
    }
}