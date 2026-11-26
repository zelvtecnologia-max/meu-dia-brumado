-- V1: Create initial schema for Sistema de Monitoramento
-- Tabelas principais do sistema

-- Tabela de usuários
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    nome_completo VARCHAR(200) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    matricula VARCHAR(30) UNIQUE,
    cargo_funcao VARCHAR(100),
    orgao_lotacao VARCHAR(200),
    telefone VARCHAR(15),
    celular VARCHAR(15),
    perfil VARCHAR(30) NOT NULL DEFAULT 'OPERADOR',
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    ultimo_login DATETIME,
    tentativas_login_falhas INT DEFAULT 0,
    bloqueado_ate DATETIME,
    senha_expira_em DATE,
    deve_trocar_senha BOOLEAN DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_matricula (matricula),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de pessoas
CREATE TABLE pessoas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(200) NOT NULL,
    nome_social VARCHAR(200),
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    genero VARCHAR(20),
    nome_mae VARCHAR(200),
    nome_pai VARCHAR(200),
    telefone VARCHAR(15),
    celular VARCHAR(15),
    email VARCHAR(100),
    endereco TEXT,
    numero_endereco VARCHAR(20),
    complemento_endereco VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    cep VARCHAR(9),
    naturalidade_cidade VARCHAR(100),
    naturalidade_estado VARCHAR(2),
    nacionalidade VARCHAR(50),
    rg VARCHAR(20),
    orgao_emissor_rg VARCHAR(20),
    uf_rg VARCHAR(2),
    data_emissao_rg DATE,
    foto_url VARCHAR(500),
    observacoes TEXT,
    tipo_pessoa VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_cpf (cpf),
    INDEX idx_email (email),
    INDEX idx_tipo_pessoa (tipo_pessoa),
    INDEX idx_cidade_estado (cidade, estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de casos
CREATE TABLE casos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_caso VARCHAR(50) NOT NULL UNIQUE,
    tipo_caso VARCHAR(50) NOT NULL,
    descricao TEXT,
    data_abertura DATE NOT NULL,
    data_encerramento DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'ABERTO',
    grau_risco VARCHAR(20),
    observacoes TEXT,
    vitima_id BIGINT NOT NULL,
    agressor_principal_id BIGINT,
    responsavel_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (vitima_id) REFERENCES pessoas(id),
    FOREIGN KEY (agressor_principal_id) REFERENCES pessoas(id),
    FOREIGN KEY (responsavel_id) REFERENCES usuarios(id),
    INDEX idx_numero_caso (numero_caso),
    INDEX idx_vitima (vitima_id),
    INDEX idx_agressor (agressor_principal_id),
    INDEX idx_responsavel (responsavel_id),
    INDEX idx_status (status),
    INDEX idx_grau_risco (grau_risco),
    INDEX idx_data_abertura (data_abertura)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de participações em caso
CREATE TABLE participacoes_caso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    caso_id BIGINT NOT NULL,
    pessoa_id BIGINT NOT NULL,
    tipo_participacao VARCHAR(30) NOT NULL,
    data_inicio DATE,
    data_fim DATE,
    observacoes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (caso_id) REFERENCES casos(id) ON DELETE CASCADE,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id),
    INDEX idx_caso (caso_id),
    INDEX idx_pessoa (pessoa_id),
    INDEX idx_tipo_participacao (tipo_participacao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de medidas protetivas
CREATE TABLE medidas_protetivas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_processo VARCHAR(50) NOT NULL UNIQUE,
    numero_medida VARCHAR(50),
    tipo_medida VARCHAR(100) NOT NULL,
    descricao TEXT,
    data_concessao DATE NOT NULL,
    data_vencimento DATE,
    data_cumprimento DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'CONCEBIDA',
    tipo_urgencia VARCHAR(20),
    distancia_minima INT,
    locais_proibidos TEXT,
    observacoes TEXT,
    caso_id BIGINT NOT NULL,
    vitima_id BIGINT NOT NULL,
    agressor_id BIGINT NOT NULL,
    usuario_concedente_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (caso_id) REFERENCES casos(id),
    FOREIGN KEY (vitima_id) REFERENCES pessoas(id),
    FOREIGN KEY (agressor_id) REFERENCES pessoas(id),
    FOREIGN KEY (usuario_concedente_id) REFERENCES usuarios(id),
    INDEX idx_numero_processo (numero_processo),
    INDEX idx_numero_medida (numero_medida),
    INDEX idx_caso (caso_id),
    INDEX idx_vitima (vitima_id),
    INDEX idx_agressor (agressor_id),
    INDEX idx_status (status),
    INDEX idx_data_vencimento (data_vencimento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de envolvidos em medida
CREATE TABLE envolvidos_medida (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    medida_protetiva_id BIGINT NOT NULL,
    pessoa_id BIGINT NOT NULL,
    tipo_envolvimento VARCHAR(30) NOT NULL,
    data_inicio DATE,
    data_fim DATE,
    observacoes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (medida_protetiva_id) REFERENCES medidas_protetivas(id) ON DELETE CASCADE,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id),
    INDEX idx_medida (medida_protetiva_id),
    INDEX idx_pessoa (pessoa_id),
    INDEX idx_tipo_envolvimento (tipo_envolvimento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de rondas
CREATE TABLE rondas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_ronda VARCHAR(50) NOT NULL UNIQUE,
    data_ronda DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME,
    tipo_ronda VARCHAR(30),
    status VARCHAR(30) NOT NULL DEFAULT 'PLANEJADA',
    rota TEXT,
    observacoes TEXT,
    medida_protetiva_id BIGINT,
    usuario_responsavel_id BIGINT,
    usuario_executor_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (medida_protetiva_id) REFERENCES medidas_protetivas(id),
    FOREIGN KEY (usuario_responsavel_id) REFERENCES usuarios(id),
    FOREIGN KEY (usuario_executor_id) REFERENCES usuarios(id),
    INDEX idx_codigo_ronda (codigo_ronda),
    INDEX idx_data_ronda (data_ronda),
    INDEX idx_status (status),
    INDEX idx_tipo_ronda (tipo_ronda),
    INDEX idx_medida (medida_protetiva_id),
    INDEX idx_executor (usuario_executor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de ocorrências
CREATE TABLE ocorrencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_ocorrencia VARCHAR(50) NOT NULL UNIQUE,
    tipo_ocorrencia VARCHAR(50) NOT NULL,
    data_ocorrencia DATE NOT NULL,
    hora_ocorrencia TIME,
    local_ocorrencia VARCHAR(500),
    descricao TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'REGISTRADA',
    gravidade VARCHAR(20),
    medidas_tomadas TEXT,
    observacoes TEXT,
    caso_id BIGINT,
    medida_protetiva_id BIGINT,
    vitima_id BIGINT,
    agressor_id BIGINT,
    usuario_registro_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (caso_id) REFERENCES casos(id),
    FOREIGN KEY (medida_protetiva_id) REFERENCES medidas_protetivas(id),
    FOREIGN KEY (vitima_id) REFERENCES pessoas(id),
    FOREIGN KEY (agressor_id) REFERENCES pessoas(id),
    FOREIGN KEY (usuario_registro_id) REFERENCES usuarios(id),
    INDEX idx_numero_ocorrencia (numero_ocorrencia),
    INDEX idx_caso (caso_id),
    INDEX idx_medida (medida_protetiva_id),
    INDEX idx_vitima (vitima_id),
    INDEX idx_agressor (agressor_id),
    INDEX idx_status (status),
    INDEX idx_gravidade (gravidade),
    INDEX idx_data_ocorrencia (data_ocorrencia)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de dados complementares do agressor
CREATE TABLE dados_complementares_agressor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pessoa_id BIGINT NOT NULL UNIQUE,
    profissao VARCHAR(100),
    local_trabalho VARCHAR(200),
    renda_mensal DECIMAL(10,2),
    escolaridade VARCHAR(50),
    possui_porte_arma BOOLEAN,
    numero_porte_arma VARCHAR(50),
    tipo_arma VARCHAR(50),
    antecedentes_criminais BOOLEAN,
    descricao_antecedentes TEXT,
    medida_cautelar_ativa BOOLEAN,
    descricao_medida_cautelar TEXT,
    uso_substancias BOOLEAN,
    tipo_substancia VARCHAR(100),
    tratamento_saude_mental BOOLEAN,
    observacoes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id) ON DELETE CASCADE,
    INDEX idx_pessoa (pessoa_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de veículos relacionados
CREATE TABLE veiculos_relacionados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pessoa_id BIGINT NOT NULL,
    placa VARCHAR(10) NOT NULL,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    cor VARCHAR(30),
    ano_fabricacao INT,
    ano_modelo INT,
    tipo_veiculo VARCHAR(30),
    renavam VARCHAR(20),
    chassi VARCHAR(50),
    observacoes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id) ON DELETE CASCADE,
    UNIQUE KEY uk_placa (placa),
    INDEX idx_pessoa (pessoa_id),
    INDEX idx_placa (placa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de fatores de risco
CREATE TABLE fatores_risco (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    caso_id BIGINT NOT NULL,
    codigo_fator VARCHAR(20) NOT NULL,
    descricao TEXT NOT NULL,
    categoria VARCHAR(30),
    nivel VARCHAR(20),
    data_identificacao DATE NOT NULL,
    data_reavaliacao DATE,
    observacoes TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (caso_id) REFERENCES casos(id) ON DELETE CASCADE,
    INDEX idx_caso (caso_id),
    INDEX idx_codigo_fator (codigo_fator),
    INDEX idx_categoria (categoria),
    INDEX idx_nivel (nivel),
    INDEX idx_ativo (ativo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de dependentes relacionados
CREATE TABLE dependentes_relacionados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pessoa_responsavel_id BIGINT NOT NULL,
    nome_completo VARCHAR(200) NOT NULL,
    data_nascimento DATE NOT NULL,
    genero VARCHAR(20),
    grau_parentesco VARCHAR(30) NOT NULL,
    cpf VARCHAR(14),
    estuda BOOLEAN,
    escola VARCHAR(200),
    possui_deficiencia BOOLEAN,
    tipo_deficiencia VARCHAR(100),
    uso_medicacao_continua BOOLEAN,
    observacoes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pessoa_responsavel_id) REFERENCES pessoas(id) ON DELETE CASCADE,
    INDEX idx_responsavel (pessoa_responsavel_id),
    INDEX idx_cpf (cpf),
    INDEX idx_grau_parentesco (grau_parentesco)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de evidências
CREATE TABLE evidencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_evidencia VARCHAR(50) NOT NULL UNIQUE,
    tipo_evidencia VARCHAR(50) NOT NULL,
    descricao TEXT,
    arquivo_url VARCHAR(500),
    hash_arquivo VARCHAR(100),
    data_coleta DATE NOT NULL,
    local_coleta VARCHAR(500),
    cadeia_custodia TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'COLETADA',
    ocorrencia_id BIGINT,
    caso_id BIGINT,
    usuario_coletor_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (ocorrencia_id) REFERENCES ocorrencias(id),
    FOREIGN KEY (caso_id) REFERENCES casos(id),
    FOREIGN KEY (usuario_coletor_id) REFERENCES usuarios(id),
    INDEX idx_codigo_evidencia (codigo_evidencia),
    INDEX idx_ocorrencia (ocorrencia_id),
    INDEX idx_caso (caso_id),
    INDEX idx_status (status),
    INDEX idx_tipo_evidencia (tipo_evidencia)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de testemunhas
CREATE TABLE testemunhas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pessoa_id BIGINT NOT NULL,
    ocorrencia_id BIGINT,
    caso_id BIGINT,
    tipo_depoimento VARCHAR(30),
    depoimento TEXT,
    data_depoimento DATE,
    local_depoimento VARCHAR(500),
    autoridade_ouvinte VARCHAR(200),
    observacoes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id),
    FOREIGN KEY (ocorrencia_id) REFERENCES ocorrencias(id),
    FOREIGN KEY (caso_id) REFERENCES casos(id),
    INDEX idx_pessoa (pessoa_id),
    INDEX idx_ocorrencia (ocorrencia_id),
    INDEX idx_caso (caso_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de relatos de agressão anterior
CREATE TABLE relatos_agressao_anterior (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    caso_id BIGINT NOT NULL,
    data_ocorrencia DATE NOT NULL,
    local_ocorrencia VARCHAR(500),
    tipo_agressao VARCHAR(50),
    descricao TEXT,
    houve_lesao BOOLEAN,
    tipo_lesao VARCHAR(100),
    houve_boletim_ocorrencia BOOLEAN,
    numero_boletim VARCHAR(50),
    medidas_tomadas TEXT,
    observacoes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (caso_id) REFERENCES casos(id) ON DELETE CASCADE,
    INDEX idx_caso (caso_id),
    INDEX idx_data_ocorrencia (data_ocorrencia)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de informações de saúde relevantes
CREATE TABLE informacoes_saude_relevante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pessoa_id BIGINT NOT NULL,
    tipo_informacao VARCHAR(50) NOT NULL,
    descricao TEXT NOT NULL,
    data_inicio DATE,
    data_fim DATE,
    medicacao VARCHAR(500),
    profissional_responsavel VARCHAR(200),
    unidade_saude VARCHAR(200),
    observacoes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id) ON DELETE CASCADE,
    INDEX idx_pessoa (pessoa_id),
    INDEX idx_tipo_informacao (tipo_informacao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de alertas
CREATE TABLE alertas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_alerta VARCHAR(50) NOT NULL UNIQUE,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT,
    tipo_alerta VARCHAR(30) NOT NULL,
    prioridade VARCHAR(20) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'NAO_LIDO',
    caso_id BIGINT,
    medida_protetiva_id BIGINT,
    ocorrencia_id BIGINT,
    usuario_destinatario_id BIGINT,
    usuario_remetente_id BIGINT,
    data_leitura DATETIME,
    data_resposta DATETIME,
    resposta TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (caso_id) REFERENCES casos(id),
    FOREIGN KEY (medida_protetiva_id) REFERENCES medidas_protetivas(id),
    FOREIGN KEY (ocorrencia_id) REFERENCES ocorrencias(id),
    FOREIGN KEY (usuario_destinatario_id) REFERENCES usuarios(id),
    FOREIGN KEY (usuario_remetente_id) REFERENCES usuarios(id),
    INDEX idx_codigo_alerta (codigo_alerta),
    INDEX idx_destinatario (usuario_destinatario_id),
    INDEX idx_status (status),
    INDEX idx_tipo (tipo_alerta),
    INDEX idx_prioridade (prioridade),
    INDEX idx_caso (caso_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de logs de auditoria
CREATE TABLE logs_auditoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    username VARCHAR(100),
    acao VARCHAR(50) NOT NULL,
    entidade VARCHAR(50) NOT NULL,
    entidade_id BIGINT,
    dados_anteriores TEXT,
    dados_novos TEXT,
    ip_origem VARCHAR(45),
    user_agent VARCHAR(500),
    endpoint VARCHAR(200),
    metodo_http VARCHAR(10),
    status_resposta INT,
    tempo_execucao_ms BIGINT,
    observacoes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_usuario (usuario_id),
    INDEX idx_entidade (entidade, entidade_id),
    INDEX idx_acao (acao),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inserir usuário admin padrão (senha: admin123 - deve ser alterada no primeiro login)
INSERT INTO usuarios (username, nome_completo, email, senha, perfil, status, deve_trocar_senha)
VALUES ('admin', 'Administrador do Sistema', 'admin@sistema.local', '$2a$10$X7UrH5YxX5YxX5YxX5YxX.5YxX5YxX5YxX5YxX5YxX5YxX5YxX5Yx', 'ADMINISTRADOR', 'ATIVO', TRUE);