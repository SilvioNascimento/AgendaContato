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

# Executa o build focando especificamente no módulo Producer
# O Maven vai compilar o Shared automaticamente por causa da relação de dependência
RUN mvn clean package -pl AgendaContatoProducer -am -DskipTests

# ==========================================
# ETAPA 2: Execução (Run Stage)
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia apenas o arquivo .jar gerado na Etapa 1 para esta imagem limpa
COPY --from=builder /app/AgendaContatoProducer/target/*.jar app.jar

# Porta padrão exposta pela API
EXPOSE 8081

# Comando de inicialização otimizado para produção
ENTRYPOINT ["java", "-jar", "app.jar"]