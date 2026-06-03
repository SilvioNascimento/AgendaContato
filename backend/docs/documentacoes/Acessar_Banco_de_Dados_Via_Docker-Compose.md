# Acessar banco de dados via docker-compose

Aqui está o guia completo e estruturado para você dominar o acesso e a verificação do seu banco 
de dados PostgreSQL rodando dentro do contêiner do Docker.

Com essa estrutura, você conseguirá inspecionar tabelas, tipos de dados, restrições e registros 
sem precisar de ferramentas externas.

---

## 1. Como Acessar o Banco de Dados no Docker

Como vimos que o seu banco está isolado em um contêiner, o comando principal usa o `docker exec` 
para disparar o cliente de terminal do Postgres (`psql`) diretamente lá dentro.

No terminal da sua máquina, acesse a pasta onde o backend está localizado e execute o seguinte comando:

```bash
docker exec -it postgres-db-container psql -U admin -d agenda_db
```

- **`-it`:** Ativa o modo interativo com terminal (permite que você digite os comandos).
- **`postgres-db-container`:** O nome exato do contêiner do banco no seu **docker-compose.yml**.
- **`-U admin`:** O usuário administrador do banco (ajuste se mudou no .env).
- **`-d agenda_db`:** O nome do banco de dados que a aplicação está usando.

**💡 Nota:** Se o terminal mudar para agenda_db=#, você entrou com sucesso!

---

## 2. Comandos de Inspeção e Estrutura (Metadados)

Estes são os comandos nativos do psql (chamados de "barra-comandos"). Eles servem para mapear 
a arquitetura e os tipos de dados das tabelas.

### A. Verificar o tipo de dado de cada atributo (Coluna)

Para ver as colunas, os tipos de dados (`varchar`, `timestamp`, `bigint`), se aceitam valores nulos 
(`nullable`) e os valores padrão de uma tabela específica:

```sql
\d usuarios
```

- **OBS:** `usuarios` é o nome da tabela criada no banco de dados.

(Se você usou anotações como `@Table(name = "tb_usuarios")`, use `\d tb_usuarios`).

---

### B. Verificar com detalhes estendidos (Índices e Chaves Estrangeiras)

Para ver uma estrutura ainda mais profunda, incluindo chaves primárias (`PRIMARY KEY`), chaves 
estrangeiras (`FOREIGN KEY`) e constraints de unicidade (`UNIQUE`):

```sql
\d+ usuarios
```

---

### C. Listar todas as tabelas e visões (Views) existentes

Para checar se o Hibernate ou o Flyway criaram as tabelas com o nome correto:

```sql
\dt
```

---

### D. Listar todas as tabelas com tamanho no disco

Útil para ver quais tabelas estão consumindo mais dados no volume do Docker:

```sql
\dt+
```

---

## 3. Comandos SQL de Verificação de Dados (DML)

Uma vez que você confirmou que a tabela existe e quais são seus atributos, use comandos SQL 
padrões para auditar os registros. **Lembre-se de sempre terminar comandos SQL com ponto e vírgula** 
(`;`).

### A. Ver todos os registros salvos

```sql
SELECT * FROM usuarios;
```

---

### B. Ver colunas específicas e formatar a senha de forma resumida

Como a senha salva pelo `BCryptPasswordEncoder` é muito grande, você pode selecionar apenas o 
essencial para caber no terminal:

```sql
SELECT id, username, email, cargo FROM usuarios;
```

---

### C. Contar quantos registros existem na tabela

Ótimo para validar se o consumidor processou todas as mensagens enviadas pelo produtor:

```sql
SELECT count(*) AS "qtd_registros" FROM usuarios;
```

- `AS`: uma palavra-chave para dar um nome temporário a uma coluna ou tabela
durante uma consulta. Adicione ela seguido por um nome desejado. Se o apelido tiver
espaços ou acentos, coloque-o entre aspas.

---

### D. Filtrar por um usuário específico

```sql
SELECT * FROM usuarios WHERE username = 'silvio_nascimento';
```

---

## 4. Dica de Ouro: Ativar o Modo de Exibição Expandido (`\x`)

No terminal do Docker, se a sua tabela tiver muitas colunas (como `criado_em`, `atualizado_em`, 
`telefone`, etc.), a saída do `SELECT *` vai quebrar a linha e ficar muito confusa de ler.

Para resolver isso, digite o comando abaixo antes de fazer o seu `SELECT`:

```sql
\x
```

- **O que ele faz?** Ele muda o layout de exibição para "vertical". Em vez de mostrar os dados em 
colunas largas, ele mostra cada registro como um bloco de "Chave: Valor", facilitando muito a 
leitura no terminal do Docker.

Para desativar e voltar ao normal, basta digitar `\x` novamente.

---

## 5. Como Sair do Banco de Dados

Para encerrar a sessão do `psql` e voltar ao terminal normal do seu sistema operacional:

```sql
\q
```
