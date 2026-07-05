# Maven Producer

O maven Producer configura uma aplicação executável ativa (uma API REST).

A função principal deste módulo no ecossistema é ser a porta de entrada dos dados: ele expõe os endpoints HTTP para 
o mundo externo (como o seu front-end React), valida as requisições, aplica os filtros de segurança criptográfica (JWT) 
e despacha as mensagens para as filas do RabbitMQ.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.agenda</groupId>
		<artifactId>backend</artifactId>
		<version>1.0.0-SNAPSHOT</version>
		<relativePath>../pom.xml</relativePath>
	</parent>

	<artifactId>AgendaContatoProducer</artifactId>
	<version>0.0.1-SNAPSHOT</version>

	<name/>
	<description/>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>21</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.8.16</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.amqp</groupId>
			<artifactId>spring-rabbit-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>com.agenda</groupId>
			<artifactId>AgendaContatoShared</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

## 🧠 O que este código faz?

### 1. Herança e Identificação do Módulo

```xml
<parent>
   <groupId>com.agenda</groupId>
   <artifactId>backend</artifactId>
   <version>1.0.0-SNAPSHOT</version>
   <relativePath>../pom.xml</relativePath>
</parent>

<artifactId>AgendaContatoProducer</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

- `<parent>`: Assim como o Shared, este bloco vincula o Producer ao `pom.xml` raiz do Monorepo. Ele herda a versão do 
Java e as regras de gerenciamento do Spring Boot de forma centralizada.
- `<artifactId>`: Define a identidade deste microsserviço. O Maven gerará o binário final nomeado como 
`AgendaContatoProducer`.

---

### 2. Gerenciamento de Dependências (O Bloco `<dependencies>`)

Estas são as ferramentas que dão superpoderes à sua API para ela fazer o trabalho de recepção e mensageria:

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

- **O que configura:** O suporte ao protocolo AMQP através do Spring RabbitMQ. É este _starter_ que injeta a ferramenta 
`RabbitTemplate` que usamos no `UserProducer` e `ContatoProducer` para conectar com o contêiner do Docker e postar 
mensagens nas Exchanges estabelecidos.

---

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

- **O que configura:** O Spring Security. Ele ativa a esteira de filtros de proteção na API, permitindo que a gente use 
- o `JwtAuthenticationFilter` e a classe `SecurityConfig` para proteger rotas e processar tokens.

---

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

- **O que configura:** O mecanismo de validação em tempo de execução do Spring. Quando os seus Controllers recebem 
um JSON e você coloca a anotação `@Valid` `@RequestBody`, é este motor (baseado no Hibernate Validator) que intercepta 
os dados e impede que requisições com campos vazios ou e-mails inválidos passem adiante.

---

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

- **O que configura:** Transforma o projeto em uma aplicação Web RESTful. Ele traz embutido o servidor **Apache Tomcat** 
(fazendo a API rodar na porta `8081`) e fornece as anotações essenciais de controle como `@RestController`, 
`@PostMapping` e os objetos de resposta HTTP (`@ResponseStatus` e `HttpStatus`).

---

```xml
<dependency>
   <groupId>org.springdoc</groupId>
   <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
   <version>2.8.16</version>
</dependency>
```

- **O que configura:** O Swagger UI (OpenAPI 3). Ele escaneia os seus controllers automaticamente e gera aquela página 
web interativa de documentação (acessível em `http://localhost:8081/swagger-ui.html`). É uma prática indispensável no 
mercado para que os desenvolvedores front-end saibam como consumir a API.

---

### Outros detalhes

- **Injeção de Dependência Local:** No bloco `<dependencies>`, o `Producer` chama o `AgendaContatoShared`. Isso cria 
o elo na arquitetura: a API HTTP ganha o direito de enxergar e instanciar os Payloads e ENUMs definidos no Shared.
- **Limpeza Arquitetural:** Este arquivo foi completamente limpo de qualquer relação com banco de dados (Flyway, 
Driver PostgreSQL, JPA). Como a API atua puramente postando mensagens na fila do RabbitMQ, ela não carrega códigos 
de persistência pesados.

[Voltar para o Passo 4](../../documentacoes/Organizacao_Monorepo.md#-passo-4-o-módulo-produtor-agendacontatoproducer)