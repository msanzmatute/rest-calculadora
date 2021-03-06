package com.api.rest.calculadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SpringApplication Api rest calculadora
 * @author msanz
 *
 */
@SpringBootApplication
@EnableSwagger2
public class ApiRestCalculadoraApplication {

	/**
	 * Run spring application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiRestCalculadoraApplication.class, args);
	}

}
