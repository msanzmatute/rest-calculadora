/**
 * 
 */
package com.api.rest.calculadora.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.rest.calculadora.enumeration.OperacionEnum;
import com.api.rest.calculadora.factory.OperacionFactory;
import com.api.rest.calculadora.model.Operacion;

import io.corp.calculator.TracerImpl;

/**
 * Servicio que permite realizar operaciones aritméticas entre dos números
 * 
 * @see Operacion
 */
@Service
public class CalculadoraService {

	/** Factoria de operaciones*/
	@Autowired
	private OperacionFactory factory;
	
	/** Api tracer */
	@Autowired
	private TracerImpl tracer;

	/**
	 * Método que realiza una operación entre 2 números
	 * 
	 * @param operacion
	 *            operación a realizar
	 * @return resultado de la operación
	 */
	public BigDecimal calcula(final Operacion operacion) {

		final String tipoOperacion = operacion.getTipoOperacion();
		
		final BigDecimal operador1 = Optional.ofNullable(operacion.getOperador1()).orElseThrow(() -> new IllegalArgumentException("El operador 1 no es válido."));
		final BigDecimal operador2 = Optional.ofNullable(operacion.getOperador2()).orElseThrow(() -> new IllegalArgumentException("El operador 2 no es válido."));
		
		final OperacionEnum correctOp = OperacionEnum.fromValue(tipoOperacion);
		
		final BigDecimal resultado =  factory.getOperation(correctOp).apply(operador1, operador2);
		tracer.trace(resultado);
		return resultado;
	}

	

}
