/**
 * 
 */
package com.api.rest.calculadora.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.api.rest.calculadora.model.Operacion;

/**
 * Servicio que permite realizar operaciones aritméticas entre dos números
 * 
 * @see Operacion
 */
@Service
public class CalculadoraService {

	

	/**
	 * Método que realiza una operación entre 2 números
	 * 
	 * @param operacion
	 *            operación a realizar
	 * @return resultado de la operación
	 */
	public BigDecimal calcula(final Operacion operacion) {

		throw new UnsupportedOperationException("Operacíon no implementada");
	}

	

}
