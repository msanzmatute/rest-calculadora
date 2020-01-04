
La aplicación puede ser lanzada desde línea comando:
 
* JAVA 
* Maven + Docker 


## Calcula

### Request

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{
  "operador1": 1,
  "operador2": 1,
  "tipoOperacion": "+"
}' 'http://localhost:8080/api/calcula'
```

tipoOperacion puede ser : +,-,x,..

### Pre-requisitos 📋

_Si el proyecto se va a abrir desde algún IDE_
* Instalar [lombok](https://projectlombok.org/setup/overview)
* Añadir la librería libs/hamcrest-all-1.3.jar al classpath

_Tener instalado en el repositorio maven tracer-1.0.0.jar_

Ejecutar el comando maven :

```
mvn %MAVEN_CMD_LINE_ARGS% install:install-file -Dfile=libs/tracer-1.0.0.jar -DgroupId=io.corp.calculator -DartifactId=tracer -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
```

### Instalación 🔧

_Una serie de ejemplos paso a paso que te dice lo que debes ejecutar para tener un entorno de desarrollo ejecutandose_

Para la compilación del proyecto ejecutar el comando maven : 

```
mvn clean package
```

_Despligue de la aplicación_

* Java

```
java -jar target/api-rest-calculadora.jar
```

* Maven docker

```
mvn dockerfile:build -P docker -DskipTests
docker run -d --name calculadora -p 8080:8080 msanzmatute/api-rest-calculadora:latest
```

_Ejecución_

[/api/calcula](http://localhost:8080/api/calcula) 

Ejemplo de json

```
{
  "operador1": 1,
  "operador2": 1,
  "tipoOperacion": "+"
}
```

* Postman

![Postman](https://github.com/msanzmatute/images/blob/master/postman.png)


* SoapUI

![SoapUI](https://github.com/msanzmatute/images/blob/master/soapui.png)


_Swagger_

* [Swagger](http://localhost:8080/swagger-ui.html) 

## Coverage ⚙️

_Para verificar la cobertura de los Test_

Ejecutar el siguiente comando maven 

```
mvn test -P coverage
```
Una vez finalizado abrir el fichero index.html del directorio ./target/site/jacoco



## Construido con 🛠️

_Herramientas utilizadas_

* [Spring-boot](http://www.dropwizard.io/1.0.2/docs/) - El framework web usado
* [lombok](https://projectlombok.org/) - Usado para la autogeneración de los métodos getters/setters del modelo de la aplicación
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Swagger](https://rometools.github.io/rome/) - Usado para la documentación y también para probar la aplicación