-- Criar uma extensão para gerar id do tipo UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE contatos(
    id                  UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id             UUID NOT NULL,
    nome_contato        VARCHAR(255) NOT NULL,
    email_contato       VARCHAR(255),
    telefone_contato    VARCHAR(255),
    tipo_contato        VARCHAR(20) NOT NULL,
    descricao           TEXT NOT NULL,
    criado_em           TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em       TIMESTAMPTZ,

    FOREIGN KEY (user_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CHECK (tipo_contato IN ('PESSOA', 'EMPRESA'))
);

-- Índice de Performance: Otimiza buscas de contatos por ID de usuário logado
CREATE INDEX idx_contatos_usuario ON contatos(user_id);