# ==========================================
# ETAPA 1: Compilação (Build Stage)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copia os arquivos de configuração do Maven do topo do Monorepo
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copia a estrutura de código de todos os módulos necessários
COPY AgendaContatoShared ./AgendaContatoShared
COPY AgendaContatoProducer ./AgendaContatoProducer
COPY AgendaContatoConsumer ./AgendaContatoConsumer

# Executa o build focando especificamente no módulo Consumer
RUN mvn clean package -pl AgendaContatoConsumer -am -DskipTests

# ==========================================
# ETAPA 2: Execução (Run Stage)
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia apenas o arquivo .jar gerado na Etapa 1 para esta imagem limpa
COPY --from=builder /app/AgendaContatoConsumer/target/*.jar app.jar

# O Consumer não expõe nenhuma porta HTTP externa (web-application-type=none)

# Comando de inicialização otimizado para produção
ENTRYPOINT ["java", "-jar", "app.jar"]