# 🛠️ Guia Prático: Como Estruturar o Monorepo Multi-módulos

Trabalhar com múltiplos projetos dentro de um único repositório Git (Monorepo) utilizando **Java 21, Maven e Spring Boot** exige uma árvore de 
herança de dependências muito bem desenhada. Caso contrário, o compilador não conseguirá rastrear as classes compartilhadas.

O segredo dessa arquitetura está em criar uma **árvore genealógica** onde o projeto Raiz atua como o patriarca (gerenciando as versões globais), 
e os subprojetos herdam diretamente dele, permitindo o reaproveitamento nativo de código sem duplicidades.

[Image of Maven multi module dependency tree architecture](diagramas/Representacao_Estrutural_do_Projeto.md)

---

## 🧭 Passo 1: Organização Física das Pastas

Na raiz do seu repositório global Git, você **não** deve colocar código Java direto. A raiz serve estritamente como um orquestrador de arquivos. 
Cada subprojeto deve morar em sua própria pasta isolada.

Certifique-se de que os nomes das pastas físicas batam exatamente com os nomes declarados na tag `<modules>` do projeto pai:

```text
backend/                                 # Raiz Global do Repositório Git
│
├── pom.xml                              # 1. POM.XML PAI (O Maestro)
│
├── AgendaContatoShared/                 # 2. MÓDULO BIBLIOTECA (JAR Puro)
│   ├── src/main/java/...                # Payloads, DTOs globais, Utils e ENUMs
│   └── pom.xml
│
├── AgendaContatoProducer/               # 3. MÓDULO API (Publisher)
│   ├── src/main/java/...                # Controllers, Services e Segurança HTTP
│   └── pom.xml
│
└── AgendaContatoConsumer/               # 4. MÓDULO WORKER (Consumer)
    ├── src/main/java/...                # Listeners de fila, Entidades JPA e Banco
    └── pom.xml
```

---

## 🎼 Passo 2: O Projeto Raiz (`backend`)

O arquivo `pom.xml` localizado na raiz do repositório funciona como o Maestro do ecossistema. O papel dele é herdar as configurações-base do Spring 
Boot e listar quais são os módulos filhos que ele deve gerenciar.

Para acessar o código maven do projeto raíz, como também os detalhes sobre ela, 
[clique aqui](./codigos_exemplos/xml/maven_backend_raiz.md)

---

## 📦 Passo 3: O Módulo Compartilhado (`AgendaContatoShared`)

O `Shared` é uma biblioteca de classes de dados puras. Ele não possui um método `main`, não inicia um servidor e serve apenas como uma 
"pasta de contratos" importada pelos irmãos.

Para acessar o código maven do shared, como também outras explicações, 
[clique aqui](./codigos_exemplos/xml/maven_shared.md)

---

## 🚀 Passo 4: O Módulo Produtor (`AgendaContatoProducer`)

Este módulo é a sua API HTTP síncrona. Ele herda as propriedades da raiz e adiciona o módulo `Shared` como se fosse uma biblioteca interna externa.

Para acessar o código maven do producer, como também outras explicações,
[clique aqui](./codigos_exemplos/xml/maven_producer.md)

---

## 📥 Passo 5: O Módulo Consumidor (`AgendaContatoConsumer`)

O cérebro assíncrono do projeto. Ele não abre portas HTTP, mas é o único responsável por gerenciar a conexão física com o banco de dados PostgreSQL.

Para acessar o código maven do consumer, como também outras explicações, 
[clique aqui](./codigos_exemplos/xml/maven_consumer.md)