# Estágio de build
FROM maven:3.9.4-eclipse-temurin-17-alpine AS build

# Criar o diretório de trabalho
WORKDIR /app

# Copiar apenas o POM para resolver dependências primeiro
COPY pom.xml .

# Baixar dependências do Maven
RUN mvn dependency:go-offline -B

# Copiar todo o restante do projeto
COPY . .

# Executar o Maven para construir o projeto, ignorando testes
RUN mvn clean package -DskipTests

# Estágio final
FROM eclipse-temurin:17-jre-alpine

# Criar o diretório de trabalho no estágio final
WORKDIR /app

# Expor a porta 8080
EXPOSE 8080

# Copiar o JAR gerado no estágio de build para o diretório correto
COPY --from=build /app/target/*.jar /app/app.jar

# Definir o comando de entrada para rodar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
