# Meu Dia - Backend

Backend REST para plataforma **Meu Dia** — rede de apoio, informações de segurança e gestão administrativa para mulheres em situação de violência.

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
│   ├── JwtAuthenticationFilter.java   # Filtro JWT
│   └── JwtTokenProvider.java          # Geração/validação de tokens JWT
├── exception/
│   ├── GlobalExceptionHandler.java    # Tratamento global de exceções
│   └── RecursoNaoEncontradoException.java
├── controller/
│   ├── AuthController.java                    # Autenticação administrativa (login, register)
│   ├── UsuariaController.java                 # CRUD Usuária + perfil/perfil-apoio
│   ├── PerfilApoioController.java             # CRUD Perfil de Apoio
│   ├── InformacaoSegurancaController.java     # CRUD Informações de Segurança
│   ├── ContatoConfiancaController.java        # CRUD Contatos de Confiança
│   ├── InstituicaoController.java             # CRUD Instituições
│   ├── ServicoAtendimentoController.java      # CRUD Serviços de Atendimento
│   ├── CanalOficialController.java            # CRUD Canais Oficiais
│   ├── ConteudoEducativoController.java       # CRUD Conteúdos Educativos
│   ├── TestemunhoController.java              # CRUD Testemunhos
│   ├── ProfissionalAutorizadoController.java  # CRUD Profissionais Autorizados
│   ├── AdministradorController.java           # CRUD Administradores
│   ├── PermissaoController.java               # CRUD Permissões
│   ├── PerfilAcessoController.java            # CRUD Perfis de Acesso
│   ├── FuncaoPermitidaController.java         # CRUD Funções Permitidas
│   ├── RelatorioEstatisticoController.java    # CRUD Relatórios Estatísticos
│   ├── RegistroAuditoriaController.java       # Consulta Registros de Auditoria
│   └── ConfiguracaoPlataformaController.java  # CRUD Configurações da Plataforma
├── model/
│   ├── Usuaria.java                      # Usuária final (vítima/usuária da plataforma)
│   ├── PerfilApoio.java                  # Perfil de apoio da usuária
│   ├── InformacaoSeguranca.java          # Informações de segurança (medidas, planos, contatos)
│   ├── ContatoConfianca.java             # Contatos de confiança da rede de apoio
│   ├── Instituicao.java                  # Instituições (Delegacia, MP, CRAS, Hospital, ONG, etc.)
│   ├── ServicoAtendimento.java           # Serviços oferecidos pelas instituições
│   ├── CanalOficial.java                 # Canais oficiais de denúncia/ajuda (180, 190, etc.)
│   ├── ConteudoEducativo.java            # Conteúdos educativos (artigos, guias, vídeos)
│   ├── Testemunho.java                   # Testemunhos/relatos anônimos publicados
│   ├── UsuarioAdministrativo.java        # Classe abstrata base (UserDetails) - admin/profissional
│   ├── ProfissionalAutorizado.java       # Profissionais autorizados (assistente social, psicólogo, etc.)
│   ├── Administrador.java                # Administradores da plataforma
│   ├── Permissao.java                    # Permissões individuais (recurso + ação)
│   ├── PerfilAcesso.java                 # Perfis de acesso agrupando permissões
│   ├── FuncaoPermitida.java              # Funções permitidas por módulo
│   ├── RelatorioEstatistico.java         # Relatórios estatísticos anonimizados
│   ├── RegistroAuditoria.java            # Logs de auditoria do sistema
│   └── ConfiguracaoPlataforma.java       # Configurações globais da plataforma
└── repository/
    ├── UsuariaRepository.java
    ├── PerfilApoioRepository.java
    ├── InformacaoSegurancaRepository.java
    ├── ContatoConfiancaRepository.java
    ├── InstituicaoRepository.java
    ├── ServicoAtendimentoRepository.java
    ├── CanalOficialRepository.java
    ├── ConteudoEducativoRepository.java
    ├── TestemunhoRepository.java
    ├── UsuarioAdministrativoRepository.java
    ├── ProfissionalAutorizadoRepository.java
    ├── AdministradorRepository.java
    ├── PermissaoRepository.java
    ├── PerfilAcessoRepository.java
    ├── FuncaoPermitidaRepository.java
    ├── RelatorioEstatisticoRepository.java
    ├── RegistroAuditoriaRepository.java
    └── ConfiguracaoPlataformaRepository.java
```

---

## ⚙️ Configuração e Execução

### Pré-requisitos
- **JDK 21** (recomendado: Eclipse Temurin ou SDKMAN)
- **Maven 3.9+**
- **MySQL 8+** rodando localmente ou em container

### Variáveis de Ambiente

```bash
export DATASOURCE_URL=jdbc:mysql://localhost:3306/meu_dia?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
export DATASOURCE_USERNAME=root
export DATASOURCE_PASSWORD=sua_senha
```

> **Dica**: O `application.yml` já tem defaults (`root` / senha vazia / localhost:3306 / database `meu_dia` com auto-criação). Ajuste via variáveis de ambiente conforme seu ambiente.

### MySQL com Docker (opcional)

```bash
docker run -d \
  --name meu-dia-mysql \
  -e MYSQL_ROOT_PASSWORD=senha123 \
  -e MYSQL_DATABASE=meu_dia \
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
java -jar target/meu-dia-1.0.0-SNAPSHOT.jar
```

---

## 📚 Endpoints da API

Base path: `/api`

### Autenticação (Administrativa)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/auth/login` | Login admin/profissional (retorna JWT) |
| POST | `/auth/register` | Registrar novo administrador (requer role ADMIN) |

### 👤 Usuária Final (Público/Autenticado)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/usuarias` | Criar nova usuária |
| GET | `/usuarias/{id}` | Buscar por ID |
| GET | `/usuarias` | Listar paginado |
| GET | `/usuarias/buscar?termo=` | Buscar por nome/email |
| GET | `/usuarias/status/{status}` | Filtrar por status |
| PUT | `/usuarias/{id}` | Atualizar |
| DELETE | `/usuarias/{id}` | Remover |
| GET | `/usuarias/{id}/informacoes` | Consultar informações |
| PUT | `/usuarias/{id}/perfil-apoio` | Gerenciar perfil de apoio |

### 🛡️ Perfil de Apoio
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/perfis-apoio` | Criar |
| GET | `/perfis-apoio/{id}` | Buscar por ID |
| GET | `/perfis-apoio/usuaria/{usuariaId}` | Buscar por usuária |
| PUT | `/perfis-apoio/{id}` | Atualizar |
| DELETE | `/perfis-apoio/{id}` | Remover |
| POST | `/perfis-apoio/{id}/organizar` | Organizar informações |
| POST | `/perfis-apoio/{id}/contatos` | Adicionar contato |

### 🔒 Informações de Segurança
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/informacoes-seguranca` | Registrar |
| GET | `/informacoes-seguranca/{id}` | Buscar por ID |
| GET | `/informacoes-seguranca/usuaria/{usuariaId}` | Listar da usuária (ativas) |
| GET | `/informacoes-seguranca/categoria/{categoria}` | Filtrar por categoria |
| PUT | `/informacoes-seguranca/{id}` | Atualizar |
| DELETE | `/informacoes-seguranca/{id}` | Remover |
| POST | `/informacoes-seguranca/{id}/registrar` | Registrar |
| PUT | `/informacoes-seguranca/{id}/atualizar` | Atualizar |
| GET | `/informacoes-seguranca/{id}/consultar` | Consultar |

**Categorias:** MEDIDA_PROTETIVA, PLANO_SEGURANCA, CONTATO_EMERGENCIA, LOCAL_SEGURO, DOCUMENTO_IMPORTANTE, OUTRO

### 📞 Contatos de Confiança
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/contatos-confianca` | Adicionar |
| GET | `/contatos-confianca/{id}` | Buscar por ID |
| GET | `/contatos-confianca/usuaria/{usuariaId}` | Listar da usuária (ativos) |
| GET | `/contatos-confianca/usuaria/{usuariaId}/principal` | Contatos principais |
| PUT | `/contatos-confianca/{id}` | Atualizar |
| DELETE | `/contatos-confianca/{id}` | Remover (soft delete) |
| POST | `/contatos-confianca/{id}/adicionar` | Adicionar |
| PUT | `/contatos-confianca/{id}/atualizar` | Atualizar |
| DELETE | `/contatos-confianca/{id}/remover` | Remover |

### 🏢 Instituições
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/instituicoes` | Cadastrar |
| GET | `/instituicoes/{id}` | Buscar por ID |
| GET | `/instituicoes` | Listar paginado |
| GET | `/instituicoes/ativas` | Listar ativas |
| GET | `/instituicoes/tipo/{tipo}` | Filtrar por tipo |
| GET | `/instituicoes/buscar?nome=` | Buscar por nome |
| PUT | `/instituicoes/{id}` | Atualizar |
| DELETE | `/instituicoes/{id}` | Remover |

**Tipos:** DELEGACIA, MINISTERIO_PUBLICO, DEFENSORIA_PUBLICA, CRAS, CREAS, CENTRO_REFERENCIA, HOSPITAL, UBS, ONG, CONSELHO_TUTELAR, OUTRO

### 🏥 Serviços de Atendimento
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/servicos-atendimento` | Cadastrar |
| GET | `/servicos-atendimento/{id}` | Buscar por ID |
| GET | `/servicos-atendimento/instituicao/{instituicaoId}` | Listar da instituição |
| GET | `/servicos-atendimento/ativos` | Listar ativos |
| PUT | `/servicos-atendimento/{id}` | Atualizar |
| DELETE | `/servicos-atendimento/{id}` | Remover |

### 📞 Canais Oficiais
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/canais-oficiais` | Cadastrar |
| GET | `/canais-oficiais/{id}` | Buscar por ID |
| GET | `/canais-oficiais` | Listar ativos |
| GET | `/canais-oficiais/buscar?finalidade=` | Buscar por finalidade |
| PUT | `/canais-oficiais/{id}` | Atualizar |
| DELETE | `/canais-oficiais/{id}` | Remover |
| GET | `/canais-oficiais/{id}/consultar` | Consultar |
| GET | `/canais-oficiais/{id}/acessar` | Acessar canal |

### 📚 Conteúdos Educativos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/conteudos-educativos` | Cadastrar |
| GET | `/conteudos-educativos/{id}` | Buscar por ID |
| GET | `/conteudos-educativos` | Listar paginado |
| GET | `/conteudos-educativos/status/{status}` | Filtrar por status |
| GET | `/conteudos-educativos/categoria/{categoria}` | Filtrar por categoria |
| GET | `/conteudos-educativos/publicados` | Listar publicados |
| GET | `/conteudos-educativos/buscar?titulo=` | Buscar por título |
| PUT | `/conteudos-educativos/{id}` | Atualizar |
| DELETE | `/conteudos-educativos/{id}` | Remover |

**Status:** RASCUNHO, PUBLICADO, ARQUIVADO, EM_REVISAO

### 💬 Testemunhos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/testemunhos` | Criar |
| GET | `/testemunhos/{id}` | Buscar por ID |
| GET | `/testemunhos` | Listar paginado |
| GET | `/testemunhos/status/{status}` | Filtrar por status |
| GET | `/testemunhos/publicados` | Listar publicados (ordenados por data) |
| GET | `/testemunhos/anonimos` | Listar anônimos |
| PUT | `/testemunhos/{id}` | Atualizar |
| DELETE | `/testemunhos/{id}` | Remover |
| POST | `/testemunhos/{id}/publicar` | Publicar testemunho |

**Status:** PENDENTE, PUBLICADO, REJEITADO, ARQUIVADO

---

### 👔 Administrativo (Requer autenticação + roles)

#### Profissionais Autorizados
| Método | Endpoint | Descrição | Roles |
|--------|----------|-----------|-------|
| POST | `/profissionais` | Criar | ADMIN |
| GET | `/profissionais/{id}` | Buscar por ID | ADMIN, PROFISSIONAL |
| GET | `/profissionais` | Listar paginado | ADMIN |
| GET | `/profissionais/orgao/{orgao}` | Filtrar por órgão | ADMIN |
| GET | `/profissionais/especialidade/{esp}` | Filtrar por especialidade | ADMIN |
| GET | `/profissionais/registro/{registro}` | Buscar por registro | ADMIN |
| PUT | `/profissionais/{id}` | Atualizar | ADMIN |
| DELETE | `/profissionais/{id}` | Remover | ADMIN |
| GET | `/profissionais/{id}/funcoes-permitidas` | Consultar funções | ADMIN, PROFISSIONAL |

#### Administradores
| Método | Endpoint | Descrição | Roles |
|--------|----------|-----------|-------|
| POST | `/administradores` | Criar | ADMIN |
| GET | `/administradores/{id}` | Buscar por ID | ADMIN |
| GET | `/administradores` | Listar paginado | ADMIN |
| GET | `/administradores/gerenciar-usuarios` | Listar gestores de usuários | ADMIN |
| GET | `/administradores/nivel/{nivel}` | Filtrar por nível | ADMIN |
| PUT | `/administradores/{id}` | Atualizar | ADMIN |
| DELETE | `/administradores/{id}` | Remover | ADMIN |
| POST | `/administradores/{id}/gerenciar-conteudos` | Gerenciar conteúdos | ADMIN |
| POST | `/administradores/{id}/gerenciar-diretorio` | Gerenciar diretório | ADMIN |
| POST | `/administradores/{id}/gerenciar-usuarios` | Gerenciar usuários | ADMIN |
| GET | `/administradores/{id}/relatorios` | Consultar relatórios | ADMIN |
| GET | `/administradores/{id}/auditoria` | Consultar auditoria | ADMIN |
| POST | `/administradores/{id}/gerenciar-configuracoes` | Gerenciar configurações | ADMIN |

#### Permissões
| Método | Endpoint | Descrição | Roles |
|--------|----------|-----------|-------|
| POST | `/permissoes` | Criar | ADMIN |
| GET | `/permissoes/{id}` | Buscar por ID | ADMIN |
| GET | `/permissoes` | Listar paginado | ADMIN |
| GET | `/permissoes/recurso/{recurso}` | Filtrar por recurso | ADMIN |
| GET | `/permissoes/recurso/{recurso}/acao/{acao}` | Filtrar por recurso+ação | ADMIN |
| PUT | `/permissoes/{id}` | Atualizar | ADMIN |
| DELETE | `/permissoes/{id}` | Remover | ADMIN |
| GET | `/permissoes/{id}/verificar-acesso` | Verificar acesso | ADMIN |

#### Perfis de Acesso
| Método | Endpoint | Descrição | Roles |
|--------|----------|-----------|-------|
| POST | `/perfis-acesso` | Criar | ADMIN |
| GET | `/perfis-acesso/{id}` | Buscar por ID | ADMIN |
| GET | `/perfis-acesso` | Listar paginado | ADMIN |
| GET | `/perfis-acesso/ativos` | Listar ativos | ADMIN |
| GET | `/perfis-acesso/nome/{nome}` | Buscar por nome | ADMIN |
| PUT | `/perfis-acesso/{id}` | Atualizar | ADMIN |
| DELETE | `/perfis-acesso/{id}` | Remover | ADMIN |
| POST | `/perfis-acesso/{id}/definir-permissoes` | Definir permissões | ADMIN |
| PUT | `/perfis-acesso/{id}/atualizar` | Atualizar perfil | ADMIN |

#### Funções Permitidas
| Método | Endpoint | Descrição | Roles |
|--------|----------|-----------|-------|
| POST | `/funcoes-permitidas` | Criar | ADMIN |
| GET | `/funcoes-permitidas/{id}` | Buscar por ID | ADMIN |
| GET | `/funcoes-permitidas` | Listar paginado | ADMIN |
| GET | `/funcoes-permitidas/modulo/{modulo}` | Filtrar por módulo | ADMIN |
| GET | `/funcoes-permitidas/ativas` | Listar ativas | ADMIN |
| PUT | `/funcoes-permitidas/{id}` | Atualizar | ADMIN |
| DELETE | `/funcoes-permitidas/{id}` | Remover | ADMIN |

#### Relatórios Estatísticos
| Método | Endpoint | Descrição | Roles |
|--------|----------|-----------|-------|
| POST | `/relatorios-estatisticos` | Criar | ADMIN |
| GET | `/relatorios-estatisticos/{id}` | Buscar por ID | ADMIN |
| GET | `/relatorios-estatisticos` | Listar paginado | ADMIN |
| GET | `/relatorios-estatisticos/periodo/{periodo}` | Filtrar por período | ADMIN |
| GET | `/relatorios-estatisticos/data?inicio=&fim=` | Filtrar por data | ADMIN |
| GET | `/relatorios-estatisticos/gerado-por/{usuarioId}` | Filtrar por gerado por | ADMIN |
| GET | `/relatorios-estatisticos/status/{status}` | Filtrar por status | ADMIN |
| PUT | `/relatorios-estatisticos/{id}` | Atualizar | ADMIN |
| DELETE | `/relatorios-estatisticos/{id}` | Remover | ADMIN |
| POST | `/relatorios-estatisticos/{id}/gerar` | Gerar relatório | ADMIN |
| GET | `/relatorios-estatisticos/{id}/consultar` | Consultar | ADMIN |

**Status:** GERANDO, GERADO, ERRO, ARQUIVADO

#### Registros de Auditoria
| Método | Endpoint | Descrição | Roles |
|--------|----------|-----------|-------|
| GET | `/registros-auditoria/{id}` | Buscar por ID | ADMIN |
| GET | `/registros-auditoria` | Listar paginado | ADMIN |
| GET | `/registros-auditoria/usuario/{usuarioId}/tipo/{tipoUsuario}` | Filtrar por usuário+tipo | ADMIN |
| GET | `/registros-auditoria/entidade/{entidade}` | Filtrar por entidade | ADMIN |
| GET | `/registros-auditoria/periodo?inicio=&fim=` | Filtrar por período | ADMIN |
| GET | `/registros-auditoria/acao/{acao}` | Filtrar por ação | ADMIN |
| GET | `/registros-auditoria/erros` | Listar apenas erros | ADMIN |
| GET | `/registros-auditoria/{id}/consultar` | Consultar | ADMIN |

#### Configurações da Plataforma
| Método | Endpoint | Descrição | Roles |
|--------|----------|-----------|-------|
| POST | `/configuracoes` | Criar | ADMIN |
| GET | `/configuracoes/{id}` | Buscar por ID | ADMIN |
| GET | `/configuracoes` | Listar paginado | ADMIN |
| GET | `/configuracoes/nome/{nome}` | Buscar por nome | ADMIN |
| GET | `/configuracoes/categoria/{categoria}` | Filtrar por categoria | ADMIN |
| GET | `/configuracoes/editaveis` | Listar editáveis | ADMIN |
| PUT | `/configuracoes/{id}` | Atualizar | ADMIN |
| DELETE | `/configuracoes/{id}` | Remover | ADMIN |
| PUT | `/configuracoes/{id}/atualizar` | Atualizar config | ADMIN |
| GET | `/configuracoes/{id}/consultar` | Consultar | ADMIN |

---

### Health Checks
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/health` | Status geral |
| GET | `/actuator/health` | Health check detalhado |

---

## 🔐 Autenticação

A API usa **JWT (JSON Web Tokens)** stateless para usuários administrativos (Administrador, ProfissionalAutorizado).

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@meudia.org",
    "senha": "senha123"
  }'
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "id": 1,
  "nome": "Administrador",
  "email": "admin@meudia.org"
}
```

### Usar o token
```bash
curl -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  http://localhost:8080/api/usuarias
```

### Roles Disponíveis
- **ADMINISTRADOR** - Acesso total à gestão da plataforma
- **PROFISSIONAL_AUTORIZADO** - Acesso às funções permitidas do seu perfil

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
- [ ] Frontend (React/Vue/Angular) - App Mobile Meu Dia
- [ ] LGPD compliance (anonimização, direito ao esquecimento)

---

## 📄 Licença

Projeto interno — **Meu Dia Platform**.