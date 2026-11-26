# Sistema de Monitoramento - Backend

Backend REST para sistema de monitoramento de medidas protetivas, casos de violência doméstica e acompanhamento de vítimas e agressores.

## 🚀 Stack Tecnológica

| Camada | Tecnologia |
|--------|------------|
| **Linguagem** | Java 21 |
| **Framework** | Spring Boot 3.3.x |
| **Build** | Maven 3.9+ |
| **Banco** | MySQL 8+ |
| **ORM** | Spring Data JPA / Hibernate |
| **Migrações** | Flyway |
| **Boilerplate** | Lombok |
| **Hot Reload** | Spring Boot DevTools |
| **Segurança** | Spring Security + JWT |
| **Testes** | JUnit 5, AssertJ, Mockito |
| **Documentação** | SpringDoc OpenAPI (planejado) |

---

## 📁 Estrutura do Projeto

```
src/main/java/com/sistema/monitoramento/
├── MonitoramentoApplication.java      # Classe principal
├── config/
│   └── SecurityConfig.java            # Configuração Spring Security + JWT
├── security/
│   └── JwtAuthenticationFilter.java   # Filtro JWT
├── exception/
│   ├── GlobalExceptionHandler.java    # Tratamento global de exceções
│   └── RecursoNaoEncontradoException.java
├── controller/
│   ├── AuthController.java            # Autenticação (login, register, refresh)
│   ├── PessoaController.java          # CRUD Pessoas
│   ├── CasoController.java            # CRUD Casos
│   ├── MedidaProtetivaController.java # CRUD Medidas Protetivas
│   ├── RondaController.java           # CRUD Rondas
│   ├── OcorrenciaController.java      # CRUD Ocorrências
│   ├── AlertaController.java          # Gestão de Alertas
│   └── RelatorioController.java       # Relatórios e Dashboard
├── model/
│   ├── Usuario.java                   # Usuário do sistema (Spring Security)
│   ├── Pessoa.java                    # Pessoa (vítima, agressor, testemunha, etc.)
│   ├── Caso.java                      # Caso de monitoramento
│   ├── ParticipacaoCaso.java          # Participação de pessoa em caso
│   ├── MedidaProtetiva.java           # Medida protetiva judicial
│   ├── EnvolvidoMedida.java           # Pessoas envolvidas na medida
│   ├── Ronda.java                     # Ronda de fiscalização
│   ├── Ocorrencia.java                # Ocorrência registrada
│   ├── DadosComplementaresAgressor.java
│   ├── VeiculoRelacionado.java
│   ├── FatorRisco.java
│   ├── DependenteRelacionado.java
│   ├── Evidencia.java
│   ├── Testemunha.java
│   ├── RelatoAgressaoAnterior.java
│   ├── InformacaoSaudeRelevante.java
│   ├── Alerta.java
│   └── LogAuditoria.java
└── repository/
    ├── UsuarioRepository.java
    ├── PessoaRepository.java
    ├── CasoRepository.java
    ├── ParticipacaoCasoRepository.java
    ├── MedidaProtetivaRepository.java
    ├── EnvolvidoMedidaRepository.java
    ├── RondaRepository.java
    ├── OcorrenciaRepository.java
    ├── DadosComplementaresAgressorRepository.java
    ├── VeiculoRelacionadoRepository.java
    ├── FatorRiscoRepository.java
    ├── DependenteRelacionadoRepository.java
    ├── EvidenciaRepository.java
    ├── TestemunhaRepository.java
    ├── RelatoAgressaoAnteriorRepository.java
    ├── InformacaoSaudeRelevanteRepository.java
    ├── AlertaRepository.java
    └── LogAuditoriaRepository.java
```

---

## ⚙️ Configuração e Execução

### Pré-requisitos
- **JDK 21** (recomendado: Eclipse Temurin ou SDKMAN)
- **Maven 3.9+**
- **MySQL 8+** rodando localmente ou em container

### Variáveis de Ambiente

```bash
export DATASOURCE_URL=jdbc:mysql://localhost:3306/sistema_monitoramento?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
export DATASOURCE_USERNAME=root
export DATASOURCE_PASSWORD=sua_senha
```

> **Dica**: O `application.yml` já tem defaults (`root` / senha vazia / localhost:3306 / database `sistema_monitoramento` com auto-criação). Ajuste via variáveis de ambiente conforme seu ambiente.

### MySQL com Docker (opcional)

```bash
docker run -d \
  --name sistema-monitoramento-mysql \
  -e MYSQL_ROOT_PASSWORD=senha123 \
  -e MYSQL_DATABASE=sistema_monitoramento \
  -p 3306:3306 \
  mysql:8
```

### Rodar a Aplicação

```bash
cd meu-dia-api
./mvnw spring-boot:run
```

- API: http://localhost:8080/api
- Health: http://localhost:8080/api/health
- Swagger UI (planejado): http://localhost:8080/api/swagger-ui.html

> Com **DevTools**, alterações no código Java reiniciam automaticamente a aplicação (hot reload).

### Build e Testes

```bash
# Compilar e rodar testes
./mvnw clean test

# Apenas compilar (skip tests)
./mvnw clean package -DskipTests

# Gerar JAR executável
./mvnw clean package
java -jar target/sistema-monitoramento-1.0.0-SNAPSHOT.jar
```

---

## 📚 Endpoints da API

Base path: `/api`

### Autenticação
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/auth/login` | Login (retorna JWT) |
| POST | `/auth/registrar` | Registrar novo usuário |
| POST | `/auth/refresh` | Renovar token |
| POST | `/auth/logout` | Logout |
| GET | `/auth/me` | Dados do usuário logado |
| POST | `/auth/alterar-senha` | Alterar senha |

### Health Checks
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/health` | Status geral |
| GET | `/actuator/health` | Health check detalhado |

### Pessoas
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/pessoas` | Listar paginado |
| GET | `/pessoas/{id}` | Buscar por ID |
| GET | `/pessoas/buscar?termo=` | Buscar por nome/CPF/email |
| GET | `/pessoas/tipo/{tipo}` | Filtrar por tipo (VITIMA, AGRESSOR, etc.) |
| GET | `/pessoas/cpf/{cpf}` | Buscar por CPF |
| POST | `/pessoas` | Criar nova |
| PUT | `/pessoas/{id}` | Atualizar |
| DELETE | `/pessoas/{id}` | Remover |

### Casos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/casos` | Listar paginado |
| GET | `/casos/{id}` | Buscar por ID |
| GET | `/casos/buscar?termo=` | Buscar por número/descrição |
| GET | `/casos/status/{status}` | Filtrar por status |
| GET | `/casos/vitima/{vitimaId}` | Casos de uma vítima |
| GET | `/casos/grau-risco/{grau}` | Filtrar por grau de risco |
| GET | `/casos/numero/{numeroCaso}` | Buscar por número |
| POST | `/casos` | Criar novo |
| PUT | `/casos/{id}` | Atualizar |
| DELETE | `/casos/{id}` | Remover |

### Medidas Protetivas
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/medidas-protetivas` | Listar paginado |
| GET | `/medidas-protetivas/{id}` | Buscar por ID |
| GET | `/medidas-protetivas/status/{status}` | Filtrar por status |
| GET | `/medidas-protetivas/caso/{casoId}` | Medidas de um caso |
| GET | `/medidas-protetivas/vitima/{vitimaId}` | Medidas de uma vítima |
| GET | `/medidas-protetivas/vencendo?inicio=&fim=` | Medidas vencendo no período |
| GET | `/medidas-protetivas/vencidas?hoje=` | Medidas vencidas |
| GET | `/medidas-protetivas/numero-processo/{numero}` | Buscar por número do processo |
| POST | `/medidas-protetivas` | Criar nova |
| PUT | `/medidas-protetivas/{id}` | Atualizar |
| DELETE | `/medidas-protetivas/{id}` | Remover |

### Rondas
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/rondas` | Listar paginado |
| GET | `/rondas/{id}` | Buscar por ID |
| GET | `/rondas/status/{status}` | Filtrar por status |
| GET | `/rondas/data/{data}` | Rondas de uma data |
| GET | `/rondas/periodo?inicio=&fim=` | Rondas por período |
| GET | `/rondas/executor/{executorId}` | Rondas de um executor |
| GET | `/rondas/hoje` | Rondas agendadas para hoje |
| GET | `/rondas/codigo/{codigo}` | Buscar por código |
| POST | `/rondas` | Criar nova |
| PUT | `/rondas/{id}` | Atualizar |
| DELETE | `/rondas/{id}` | Remover |

### Ocorrências
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/ocorrencias` | Listar paginado |
| GET | `/ocorrencias/{id}` | Buscar por ID |
| GET | `/ocorrencias/buscar?termo=` | Buscar por termo |
| GET | `/ocorrencias/status/{status}` | Filtrar por status |
| GET | `/ocorrencias/gravidade/{gravidade}` | Filtrar por gravidade |
| GET | `/ocorrencias/data/{data}` | Ocorrências de uma data |
| GET | `/ocorrencias/periodo?inicio=&fim=` | Ocorrências por período |
| GET | `/ocorrencias/caso/{casoId}` | Ocorrências de um caso |
| GET | `/ocorrencias/numero/{numero}` | Buscar por número |
| POST | `/ocorrencias` | Criar nova |
| PUT | `/ocorrencias/{id}` | Atualizar |
| DELETE | `/ocorrencias/{id}` | Remover |

### Alertas
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/alertas` | Listar paginado |
| GET | `/alertas/{id}` | Buscar por ID |
| GET | `/alertas/meus?usuarioId=&status=` | Meus alertas |
| GET | `/alertas/nao-lidos/{usuarioId}` | Contar não lidos |
| GET | `/alertas/status/{status}` | Filtrar por status |
| GET | `/alertas/tipo/{tipo}` | Filtrar por tipo |
| GET | `/alertas/prioridade/{prioridade}` | Filtrar por prioridade |
| GET | `/alertas/codigo/{codigo}` | Buscar por código |
| PUT | `/alertas/{id}/ler` | Marcar como lido |
| PUT | `/alertas/{id}/responder` | Responder alerta |
| POST | `/alertas` | Criar novo |
| PUT | `/alertas/{id}` | Atualizar |
| DELETE | `/alertas/{id}` | Remover |

### Relatórios
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/relatorios/dashboard` | Dashboard geral |
| GET | `/relatorios/casos-por-status` | Casos agrupados por status |
| GET | `/relatorios/medidas-por-status` | Medidas agrupadas por status |
| GET | `/relatorios/ocorrencias-por-gravidade` | Ocorrências por gravidade |
| GET | `/relatorios/casos-por-grau-risco` | Casos por grau de risco |
| GET | `/relatorios/fatores-risco-por-nivel` | Fatores de risco por nível |
| GET | `/relatorios/rondas-por-status` | Rondas por status |
| GET | `/relatorios/alertas-por-tipo` | Alertas por tipo |
| GET | `/relatorios/pessoas-por-tipo` | Pessoas por tipo |
| GET | `/relatorios/evolucao-casos?inicio=&fim=` | Evolução de casos no período |
| GET | `/relatorios/casos-vitima/{vitimaId}` | Casos de uma vítima |
| GET | `/relatorios/medidas-vencendo?dias=30` | Medidas vencendo nos próximos N dias |
| GET | `/relatorios/rondas-periodo?inicio=&fim=` | Rondas por período |

---

## 🔐 Autenticação

A API usa **JWT (JSON Web Tokens)** stateless.

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "senha": "admin123"
  }'
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "expiraEm": 86400000,
  "usuario": {
    "id": 1,
    "username": "admin",
    "nomeCompleto": "Administrador do Sistema",
    "perfil": "ADMINISTRADOR"
  }
}
```

### Usar o token
```bash
curl -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  http://localhost:8080/api/pessoas
```

### Perfis de Usuário
- **ADMINISTRADOR** - Acesso total
- **GESTOR** - Gestão de casos, medidas, usuários
- **OPERADOR** - Operações diárias (rondas, ocorrências)
- **ANALISTA** - Leitura e relatórios
- **VISUALIZADOR** - Apenas leitura

---

## 🧪 Testes

```bash
# Rodar testes unitários
./mvnw test

# Rodar com coverage
./mvnw clean test jacoco:report
```

---

## 🔧 Próximos Passos Sugeridos

- [ ] Documentação OpenAPI/Swagger (SpringDoc)
- [ ] Cache com Redis
- [ ] Métricas (Micrometer + Prometheus/Grafana)
- [ ] Logs estruturados (Logstash/ELK)
- [ ] CI/CD (GitHub Actions / GitLab CI)
- [ ] Testes de integração com Testcontainers
- [ ] Auditoria automática com `@EntityListeners`
- [ ] Notificações (email, push, WhatsApp)
- [ ] Exportação de relatórios (PDF, Excel)
- [ ] Frontend (React/Vue/Angular)

---

## 📄 Licença

Projeto interno — **Sistema de Monitoramento Platform**.