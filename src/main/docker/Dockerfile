FROM zenika/alpine-maven as build

ENV http_proxy http://alca.proxy.corp.sopra:8080
ENV https_proxy http://alca.proxy.corp.sopra:8080

WORKDIR /app
COPY * ./


RUN mvn clean package -DskipTest 

FROM openjdk:8-alpine
COPY --from=build /app/${JAR_FILE} calculadora.jar
EXPOSE 8080
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar calculadora.jar
