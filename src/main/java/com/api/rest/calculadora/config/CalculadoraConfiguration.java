package com.api.rest.calculadora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.corp.calculator.TracerImpl;

/**
 * Configuración Aplicación
 * @author msanz
 *
 */

@Configuration
public class CalculadoraConfiguration {

	/**
	 * Bean Tracer 
	 * @return {@link TracerImpl}
	 */
	@Bean
	TracerImpl tracer() {

		return new TracerImpl();

	}

	
}
