package com.api.rest.calculadora.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuración Swagger
 * @author msanz
 *
 */
@EnableSwagger2
@Configuration
@PropertySource("classpath:swagger.properties")
public class SwaggerConfiguration {

	/**
	 * Bean Calculadora 
	 * @return {@link Docket}
	 */
	@Bean
	Docket calculadoraApi() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().paths(PathSelectors.any()).build()
				.pathMapping("/").useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.POST, buildMessage());

	}

	/**
	 * Builder AppInfo
	 * @return {@link ApiInfo}
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Calculadora REST API").description("Calculadora").version("0.0.1").build();
	}

	
	/**
	 * Builder mensajes de error de la aplicación
	 * @return lista de mensajes de error 
	 */
	private List<ResponseMessage> buildMessage() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(500).message("Error interno en el servidor").build());
	}
}
