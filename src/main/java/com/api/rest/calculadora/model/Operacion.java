package com.api.rest.calculadora.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Modelo de entrada microservicio calculadora 
 * @author msanz
 *
 */
@ApiModel(description = "Modelo de la Operación Aritmética.")
public class Operacion {
	
	/** Operador 1 de la operación*/	
	@ApiModelProperty(dataType = "bigdecimal", required = true, notes = "operador 1")
	private BigDecimal operador1;
	
	/** Operador 2 de la operación*/
	@ApiModelProperty(dataType = "bigdecimal", required = true, notes = "operador 1")
	private BigDecimal operador2;
	
	/** Tipo de la operación */
	@ApiModelProperty(dataType = "string", required = true, notes = "tipo de la operación a realizar", allowableValues="+, -, /, x, %", example ="+")
	private String tipoOperacion;
}
