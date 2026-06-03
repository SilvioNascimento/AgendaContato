# Maven Backend Raíz

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.14</version> <relativePath/>
    </parent>

    <groupId>com.agenda</groupId>
    <artifactId>backend</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <modules>
        <module>AgendaContatoShared</module>
        <module>AgendaContatoProducer</module>
        <module>AgendaContatoConsumer</module>
    </modules>

    <properties>
        <java.version>21</java.version>
    </properties>
</project>
```

## 🧠 O que este código faz?

### 1. Tag de Inicialização e Metadados do Maven

- `<?xml version="1.0" encoding="UTF-8"?>`: Não é uma tag do Maven, mas sim a declaração padrão do XML. 
Ela avisa aos leitores de código que o arquivo segue a versão 1.0 do XML e utiliza a codificação de 
caracteres UTF-8 (aquela mesma que configuramos para evitar erros com acentos nos comentários).
- `<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">`:
  É a tag raiz que abre o projeto. Os atributos dentro dela (`xmlns`, `xsi`, `schemaLocation`) funcionam como 
contratos de validação. Eles dizem para a sua IDE (como o IntelliJ) onde buscar as regras oficiais 
que validam se você escreveu o `pom.xml` corretamente.
- `<modelVersion>4.0.0</modelVersion>`: Define a versão do modelo do layout do arquivo POM. Atualmente, 
o padrão absoluto para todos os projetos Maven modernos é a versão `4.0.0`. Você raramente (ou nunca) verá 
um número diferente aqui.

---

### 2. A Herança do Spring Boot (O Bloco `<parent>`)

- `<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.14</version> 
    <relativePath/>
</parent>`: Esta é uma das configurações mais importantes do ecossistema. Ela define o Projeto Pai do seu 
backend. Ao herdar do `spring-boot-starter-parent`, o seu projeto ganha magicamente uma série de configurações 
pré-fabricadas pelo time do Spring.
- O que está configurando na prática:
  - **Gerenciamento de Versões (BOM):** Você não precisará ficar adivinhando qual versão do Hibernate, do Jackson 
  ou do Flyway usar nos filhos. O Spring Boot já sabe quais versões dessas bibliotecas funcionam perfeitamente 
  - juntas na versão `3.5.14`.
  - **Compilação padrão:** Configura os plugins do Maven para compilar seu código Java adequadamente.
  - `<relativePath/>:` O comportamento padrão do Maven ao ver a tag `<parent>` é procurar o arquivo pai nas pastas 
  locais do seu computador. Ao colocar essa tag vazia, você avisa ao Maven: _"Não procure esse pai nas minhas 
  pastas locais, busque-o direto no repositório central do Maven na internet"_.

---

### 3. Identificação do seu Projeto (As Coordenadas GAV)

O Maven identifica qualquer projeto no planeta através de três coordenadas obrigatórias conhecidas como **GAV** 
(_GroupId_, _ArtifactId_, _Version_).

- `<groupId>com.agenda</groupId>`: É o identificador da organização ou do escopo do projeto. Segue a convenção 
de nomenclatura de pacotes Java (geralmente o inverso de um domínio de internet, como `com.agenda`). Todos os 
subprojetos filhos herdarão este grupo.
- `<artifactId>backend</artifactId>`: É o nome do projeto em si. Neste caso, define que este repositório global 
e unificado se chama `backend`.
- `<version>1.0.0-SNAPSHOT</version>`: Controla a versão atual do seu software. O termo `-SNAPSHOT` é uma 
palavra-chave crucial no Maven. Ela indica que o projeto está em fase de desenvolvimento ativo (uma versão 
de rascunho). Quando você terminar o projeto e colocá-lo em produção definitiva, você mudará essa tag para 
apenas `1.0.0`.
- `<packaging>pom</packaging>`: É a linha mais importante da raiz. Ela diz ao Maven que este projeto serve 
apenas para agrupar e guiar os subprojetos (uma "pasta de configurações"). Se você omitir isso, o Maven tentará 
buildar a raiz como um app Java comum e falhará. O motivo disso é porque a pasta `backend` é um projeto raíz de 
um Monorepo multi-módulos e não possui código Java solto nele.

---

### 4. A Orquestração de Módulos (O Coração do Monorepo)

- `<modules>
    <module>AgendaContatoShared</module>
    <module>AgendaContatoProducer</module>
    <module>AgendaContatoConsumer</module>
</modules>`: Vincula os três módulos no ciclo de build. Quando você rodar o comando `./mvnw clean install` na 
raiz,o Maven lê este bloco e monta o que chamamos de Reactor Build Order (Ordem de Construção do Reator). 
Ele analisa quem depende de quem (por exemplo, percebe que o `Producer` precisa do `Shared`) e cria a fila exata 
de compilação automaticamente, garantindo que o ecossistema seja construído sem erros de dependência cruzada.

---

### 5. Propriedades Globais (O Bloco `<properties>`)

- `<properties>
    <java.version>21</java.version>
</properties>`: Funciona como uma seção de declaração de variáveis globais do seu build.
  - **O que está configurando na prática:** A propriedade `<java.version>21</java.version>` dita para o compilador 
  do Maven que todo o código escrito nos módulos filhos deve ser compilado e validado utilizando os recursos 
  e a sintaxe do Java 21 (LTS). Se você tentar rodar o build usando uma versão antiga do Java (como o Java 11 
  ou 17), o Maven bloqueará a execução para garantir a integridade técnica do projeto.

[Voltar para o Passo 2](../../documentacoes/Organizacao_Monorepo.md#-passo-2-o-projeto-raiz-backend)