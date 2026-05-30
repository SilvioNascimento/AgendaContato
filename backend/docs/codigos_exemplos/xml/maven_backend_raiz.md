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

### 🧠 O que este código faz?

- `<packaging>pom</packaging>`: É a linha mais importante da raiz. Ela diz ao Maven que este projeto serve apenas para agrupar e guiar os subprojetos.
  Se você omitir isso, o Maven tentará buildar a raiz como um app Java comum e falhará.
- `<modules>`: Vincula as pastas físicas ao ciclo de build. Quando você rodar mvn clean install na raiz, o Maven lerá essa lista e descobrirá sozinho a
  ordem correta de compilação (compilando quem é dependência primeiro).

[Voltar para o Passo 2](../../documentacoes/Organizacao_Monorepo.md#-passo-2-o-projeto-raiz-backend)