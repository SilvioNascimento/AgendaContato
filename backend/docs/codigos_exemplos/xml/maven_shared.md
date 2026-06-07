# Maven Shared

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

	<artifactId>AgendaContatoShared</artifactId>
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
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-annotations</artifactId>
		</dependency>
		<dependency>
			<groupId>jakarta.persistence</groupId>
			<artifactId>jakarta.persistence-api</artifactId>
		</dependency>
		<dependency>
			<groupId>jakarta.validation</groupId>
			<artifactId>jakarta.validation-api</artifactId>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<executions>
					<execution>
						<id>default-compile</id>
						<phase>compile</phase>
						<goals>
							<goal>compile</goal>
						</goals>
						<configuration>
							<annotationProcessorPaths>
								<path>
									<groupId>org.projectlombok</groupId>
									<artifactId>lombok</artifactId>
								</path>
							</annotationProcessorPaths>
						</configuration>
					</execution>
					<execution>
						<id>default-testCompile</id>
						<phase>test-compile</phase>
						<goals>
							<goal>testCompile</goal>
						</goals>
						<configuration>
							<annotationProcessorPaths>
								<path>
									<groupId>org.projectlombok</groupId>
									<artifactId>lombok</artifactId>
								</path>
							</annotationProcessorPaths>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>

</project>
```

## 🧠 O que este código faz?

### 1. Relação de Herança (A Tag `<parent>`)

```xml
<parent>
   <groupId>com.agenda</groupId>
   <artifactId>backend</artifactId>
   <version>1.0.0-SNAPSHOT</version>
   <relativePath>../pom.xml</relativePath>
</parent>
```

- **O que faz:** Vincula este módulo filho ao seu `pom.xml` pai (Root). 
- **O que configura:** Graças a isso, o `Shared` herda automaticamente as propriedades globais do pai, como a versão do 
Java e as definições de controle do Spring Boot Parent.
- **`<relativePath>../pom.xml</relativePath>`:** Indica fisicamente onde o arquivo pai está localizado na estrutura de 
pastas da sua máquina (`../` significa "voltar uma pasta"). Isso permite que o Maven encontre o orquestrador do Monorepo 
instantaneamente.

---

### 2. Coordenadas do Submódulo

```xml
<artifactId>AgendaContatoShared</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

- **_<artifactId>_:** Define o nome específico deste artefato. Quando o Maven compilar o projeto, ele gerará um arquivo 
`.jar` com esse nome exato.
- **_<version>_:** Define a versão específica deste módulo. Note que ela está em `0.0.1-SNAPSHOT` (versão inicial de 
desenvolvimento), o que é perfeitamente normal e independente da versão `1.0.0` do pai.

_(As tags vazias como `<name/>`, `<description/>`, `<url/>`, `<licenses>`, `<developers>` e `<scm>` servem para 
documentação formal do projeto e publicação em repositórios públicos. Como é um projeto interno/portfólio, elas podem 
ficar limpas sem problemas)._

---

### 3. Gerenciamento de Dependências (O Bloco `<dependencies>`)

A tag `<dependencies>` é um gerenciamento de cada dependência definida e utilizada pelo projeto. Já a 
tag `<dependency>` conecta entre o projeto Maven e um artefato, adicionando o artefato a um ou mais classpaths
do projeto ou utilizado de outra forma durante a construção do projeto.

Estas são as ferramentas e bibliotecas específicas que as classes do seu módulo `Shared` precisam para compilar:

- **Lombok:** É uma biblioteca Java que automatiza o seu código sem precisar definir getters e setters manualmente, 
por exemplo. Com as anotações `@Getter`/`@Setter`, `@ToString` e `@AllArgsConstructor` inseridas antes da definição 
da classe, por exemplo, gera automaticamente o getter e setter padrão, o método `toString` com todos os campos e 
um construtor com cada campo da classe, respectivamente.
  - `O que configura:` Ele permite que você use anotações como `@Data`, `@Getter`, `@Setter` nas suas classes, 
  economizando linhas de código ao gerar os métodos automaticamente em tempo de compilação.
  - `<optional>true</optional>`: Significa que o Lombok é uma dependência que serve apenas para o desenvolvimento 
  deste módulo. Quando o `Producer` ou o `Consumer` importarem o `Shared`, eles não trarão o Lombok de forma "casada" se 
  não precisarem.
- **Jackson Annotations:** Anotações públicas principais utilizadas para configurar o funcionamento do 
Mapeamento/Vinculação de Dados. Por exemplo, pode mapear os campos de um DTO para facilitar o envio de dados (Ex: campo
`dataNascimento` no DTO pode ser referido como `data_de_nascimento` em JSON) como também realizar o mapeamento entre
entidades (classes e seus atributos) e banco de dados (tabelas e suas colunas).
  - O que configura: Biblioteca usada para serialização de JSON. Ela permite o uso de anotações como @JsonProperty,
  @NotBlank, @NotNull, @JsonFormat e @JsonValue em Payloads, DTOs e Enuns controlando como os atributos Java vão 
  virar chaves no formato JSON.
- **Jakarta Persistence API:** Anotações voltadas para gerenciar dados relacionais através de mapeamento de 
objeto/relacional. Jakarta Persistence foca em 4 áreas: **Jakarta Persistence**, **The query language**, 
**API de critérios de Jakarta Persistence** e **metadados de mapeamento objeto/relacional**.
  - **O que configura:** A especificação do JPA (Java Persistence API). Fornece anotações como `@Entity`, 
  `@Table`, `@Id`, `@Column`. Como você optou por compartilhar DTOs ou estruturas de dados que usam mapeamento 
  relacional, essa biblioteca é obrigatória para o compilador não quebrar.
- **Jakarta Validation API:** O Jakarta Bean Validation (ou Jakarta Validation API), segundo Jakarta EE:
  > oferece um recurso para validar
objetos, membros de objetos, métodos e construtores. Em ambientes Jakarta EE, o Jakarta Bean Validation integra-se aos 
contêineres e serviços do Jakarta EE, permitindo que os desenvolvedores definam e apliquem restrições de validação com 
facilidade. O Jakarta Bean Validation está disponível como parte da plataforma Jakarta EE.

  - O que configura: A API de validação do Bean Validation. Ela disponibiliza as anotações de validação de dados que 
  usamos no seu DTO no Shared (como `@NotBlank`, `@Email`, `@Size`), garantindo que as regras de negócio de entrada 
  fiquem centralizadas ali.

---

### 4. Configuração de Build e Plugins (O Bloco `<build>`)

```xml
<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-compiler-plugin</artifactId>
   ...
</plugin>
```

- **O que faz:** Configura o comportamento do plugin oficial de compilação do Maven (`javac`).
- **O que está configurando na prática:** O Maven precisa de uma configuração explícita para conseguir trabalhar 
junto com o Lombok. Como o Lombok gera os códigos (getters/setters) "magicamente", o compilador do Java padrão 
não os veria nativamente.

Este bloco gigante foi estruturado com duas execuções primárias:

1. `id: default-compile`: Atua na fase de compilação do código principal (`src/main/java`). Ele injeta o 
`<annotationProcessorPaths>` apontando para o Lombok, avisando ao compilador: _"Antes de transformar o código Java em 
bytecode, processe as anotações do Lombok primeiro"_.
2. `id: default-testCompile`: Faz exatamente a mesma coisa, mas para a fase de testes do projeto (`src/test/java`), 
garantindo que o Lombok também funcione caso você crie testes automatizados para as classes do Shared.

---

### Outros detalhes

- **A ausência do `spring-boot-maven-plugin`:** Como o Shared é um JAR comum de biblioteca, nós removemos o plugin de 
empacotamento do Spring Boot. Se ele estivesse aí, o Maven tentaria procurar um método `main` para rodar e quebraria o 
build.
- **Dependências de APIs (`jakarta.*`):** Colocamos apenas as interfaces de anotações (como as validações e as marcas 
de `@Entity`). Isso permite usar `@NotBlank` ou `@Enumerated` nos Payloads compartilhados sem precisar carregar os 
motores pesados do Hibernate ou do Spring Data JPA para dentro do Shared.

---

## Fontes:

- [Dependências do Maven](https://maven.apache.org/repositories/dependencies.html);
- [Projeto Lombok](https://projectlombok.org/);
- [Mapeamento objeto-relacional: como funciona e técnicas](https://blog.geekhunter.com.br/mapeamento-objeto-relacional/);
- [Pacote com.fasterxml.jackson.annotation](https://javadoc.io/doc/com.fasterxml.jackson.core/jackson-annotations/latest/com.fasterxml.jackson.annotation/com/fasterxml/jackson/annotation/package-summary.html);
- [Jakarta Persistence](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/persist/persistence-intro/persistence-intro.html);
- [Jakarta Bean Validation](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/beanvalidation/bean-validation/bean-validation.html).

---

[Voltar para o Passo 3](../../documentacoes/Organizacao_Monorepo.md#-passo-3-o-módulo-compartilhado-agendacontatoshared)