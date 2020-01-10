FROM openjdk:8-alpine
MAINTAINER msanzmatute@gmail.com

ARG JAR_FILE

VOLUME /tmp
ADD ${JAR_FILE} calculadora.jar
ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /calculadora.jar" ]