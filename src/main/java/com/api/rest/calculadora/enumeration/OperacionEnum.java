package com.api.rest.calculadora.enumeration;

import java.util.Arrays;
import java.util.Optional;


/**
 * Enum de las operaciones posibles del micro servicio
 * @author msanz
 *
 */
public enum OperacionEnum {
	SUMA("+"), RESTA("-"), MULTIPLICACION("x"), DIVISION("/"), RESTO("%");
	
	/** operación */
	private String operacion;
	
	 
	
	/**
	 * Constructor por defecto
	 * 
	 * @param operacion tipo de operación
	 */
	private OperacionEnum(String operacion) {
		this.operacion = operacion;
	}



	/**
	 * Método que devuelve el enumerado de la operación a partir de una cadena
	 * @param value operación 
	 * @return enumerado de la operación
	 */
	public static OperacionEnum fromValue(final String value) {
		final String operacionValue = Optional.ofNullable(value).orElse("");
		
		return Arrays.stream(OperacionEnum.values()).filter(op -> op.getOperacion().equalsIgnoreCase(operacionValue)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("Operación no encontrada : %s.", operacionValue)));
	
		
	}



	/**
	 * Getter de la operación 
	 * @return operación
	 */
	public String getOperacion() {
		return operacion;
	}
	
	
}
