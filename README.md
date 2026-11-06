# Meu Dia API

Backend REST para gerenciamento de registros diários — tarefas, compromissos, anotações e acompanhamento de status.

## 🚀 Stack Tecnológica

| Camada | Tecnologia |
|--------|------------|
| **Linguagem** | Java 21 |
| **Framework** | Spring Boot 3.3.x |
| **Build** | Maven 3.9+ |
| **Banco** | MySQL 8+ |
| **ORM** | Spring Data JPA / Hibernate |
| **Boilerplate** | Lombok |
| **Hot Reload** | Spring Boot DevTools |
| **Testes** | JUnit 5, AssertJ (via spring-boot-starter-test) |

---

## 📁 Estrutura do Projeto

```
src/main/java/com/meudia/api
├── MeuDiaApiApplication.java          # Classe principal
├── health/
│   └── HealthController.java          # Endpoints /health, /ready, /live
└── domain/
    ├── controller/
    │   └── DiaRegistroController.java # REST endpoints
    ├── dto/
    │   └── DiaRegistroDTO.java        # Request/Response DTOs
    ├── model/
    │   └── DiaRegistro.java           # Entidade JPA
    ├── repository/
    │   └── DiaRegistroRepository.java # Spring Data JPA
    └── service/
        └── DiaRegistroService.java    # Regras de negócio
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

> Com **DevTools**, alterações no código Java reiniciam automaticamente a aplicação (hot reload).

### Build e Testes

```bash
# Compilar e rodar testes
./mvnw clean test

# Apenas compilar (skip tests)
./mvnw clean package -DskipTests

# Gerar JAR executável
./mvnw clean package
java -jar target/meu-dia-api-0.0.1-SNAPSHOT.jar
```

---

## 📚 Endpoints da API

Base path: `/api`

### Health Checks
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/health` | Status geral |
| GET | `/health/ready` | Readiness probe |
| GET | `/health/live` | Liveness probe |

### Dia Registros
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/dia-registros` | Listar paginado (`?page=0&size=20&sort=dataRegistro,asc`) |
| GET | `/dia-registros/{id}` | Buscar por ID |
| GET | `/dia-registros/status/{status}` | Filtrar por status |
| GET | `/dia-registros/data/{data}` | Filtrar por data (`yyyy-MM-dd`) |
| GET | `/dia-registros/periodo` | Filtrar por período (`?inicio=...&fim=...`) |
| POST | `/dia-registros` | Criar novo |
| PUT | `/dia-registros/{id}` | Atualizar parcial |
| DELETE | `/dia-registros/{id}` | Remover |

### Exemplos de Requisição

**Criar registro**
```bash
curl -X POST http://localhost:8080/api/dia-registros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Reunião de alinhamento",
    "descricao": "Alinhar sprint com o time",
    "dataRegistro": "2026-11-10T09:00:00",
    "status": "PLANEJADO"
  }'
```

**Listar por status**
```bash
curl http://localhost:8080/api/dia-registros/status/PLANEJADO
```

**Listar por data**
```bash
curl http://localhost:8080/api/dia-registros/data/2026-11-10
```

**Listar por período**
```bash
curl "http://localhost:8080/api/dia-registros/periodo?inicio=2026-11-01T00:00:00&fim=2026-11-30T23:59:59"
```

---

## 🧪 Testes

```bash
# Rodar testes unitários
./mvnw test
```

---

## 🔧 Próximos Passos Sugeridos

- [ ] Autenticação/Autorização (Spring Security + JWT)
- [ ] Cache com Redis
- [ ] Métricas (Micrometer + Prometheus/Grafana)
- [ ] Logs estruturados (Logstash/ELK)
- [ ] CI/CD (GitHub Actions / GitLab CI)
- [ ] Migrações de banco (Flyway/Liquibase)
- [ ] Tratamento global de exceções (`@ControllerAdvice`)
- [ ] Documentação OpenAPI/Swagger (SpringDoc)

---

## 📄 Licença

Projeto interno — **Meu Dia Platform**.