# Estruturar o .env

## Sobre o arquivo `.env` e sua importância

Antes de estruturar o arquivo `.env`, deve saber o que ele é e a sua importância.

O arquivo `.env` é um arquivo de texto simples que armazena as suas variáveis de ambiente de trabalho personalizados
com o formato `CHAVE=valor`. Já o seu propósito é manter os dados de configuração sensíveis separados do seu código,
mantendo tais informações ocultas das pessoas que acessam o seu projeto.

---

## Criar e Configurar o `.env`

Deve criar o arquivo `.env` na raíz do projeto, como mostra a demonstração abaixo:

```text
backend
|   |
|   └──   .gitignore
└──   .env (crie o arquivo aqui)
```

_**OBSERVAÇÃO:** Antes de seguir com as instruções abaixo, a sua máquina deve ter o PostgreSQL instalado na sua 
máquina (no caso, o **pgAdmin 4**) para funcionar de forma esperada._

Em seguida, atribua as variáveis de ambiente de trabalho conforme abaixo:

```text
# =========================================================================
# CONFIGURAÇÕES DE INFRAESTRUTURA DE BANCO DE DADOS (POSTGRESQL)
# =========================================================================
POSTGRES_DB=agenda_db
POSTGRES_USER={seu_user_postgres}
POSTGRES_PASSWORD={sua_senha_postgres}
POSTGRES_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/agenda_db

# =========================================================================
# CONFIGURAÇÕES DE INFRAESTRUTURA DE MENSAGERIA (RABBITMQ)
# =========================================================================
RABBITMQ_USER={seu_user_rabbitmq}
RABBITMQ_PASSWORD={sua_senha_rabbitmq}

# =========================================================================
# CONFIGURAÇÕES DE SEGURANÇA E ASSINATURA DE CRYPTO (JWT)
# =========================================================================
JWT_SECRET={sua_jwt_secret}
```

Você pode perceber que os valores da maioria das variáveis de ambiente de trabalho estão com as chaves (`{}`).
Deve substituí-los com os respectivos valores abaixo:

- `{seu_user_postgres}`: Substitui com o nome do user cadastrado no seu PostgreSQL instalado na sua máquina;
- `{sua_senha_postgres}`: Substitui com a sua senha cadastrada no seu PostgreSQL instalado na sua máquina;
- `{seu_user_rabbitmq}`: Substitui com o nome do user do RabbitMQ conforme a sua vontade. Ex: `baba_boy`;
- `{sua_senha_rabbitmq}`: Substitui com a senha do RabbitMQ conforme a sua vontade. Ex: `baba_boy_123`;
- `{sua_jwt_secret}`: Substitui com seu jwt secret conforme a sua vontade. Ex: `minha_chave_jwt_secret_super_mega_hiper_secreta_baba_boy`;

---

## Adicionar `.env` ao `.gitignore`

Para evitar que as informações sensíveis que foi definido no `.env` serem vistas, deve adicioná-lo no arquivo
`.gitignore` (arquivo que impede que os arquivos e pastas informados nele não sejam enviados no github):

```.gitignore
# Variáveis de ambiente
.env
```

---

## Fontes

- [Arquivos .Env E A Arte De Não Commitar Segredos](https://blog.openreplay.com/pt/arquivos-env-arte-de-n%C3%A3o-comprometer-segredos/)

---

[Voltar para o README.md](../../README.md#informações-sobre-o-projeto)