-- Criar uma extensão para gerar id do tipo UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE usuarios (
    id              UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    username        VARCHAR(150) NOT NULL UNIQUE,
    email           VARCHAR(255) NOT NULL UNIQUE,
    telefone        VARCHAR(255) NOT NULL UNIQUE,
    senha           VARCHAR(255),
    cargo           VARCHAR(20) NOT NULL,
    criado_em       TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em   TIMESTAMPTZ

    CHECK (cargo IN ("ADMIN", "USUARIO"))
);

