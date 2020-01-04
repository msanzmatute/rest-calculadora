package com.api.rest.calculadora.factory;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.function.BinaryOperator;

import org.springframework.stereotype.Component;

import com.api.rest.calculadora.enumeration.OperacionEnum;
/**
 * Factoria de operaciones
 * @author msanz
 *
 */
@Component
public class OperacionFactory {

	/** Map de las operaciones permitidas*/
	private static final EnumMap<OperacionEnum, BinaryOperator<BigDecimal>> MAP_OPERACION = new EnumMap<>(
			OperacionEnum.class);

	static {
		MAP_OPERACION.put(OperacionEnum.SUMA, BigDecimal::add);
		MAP_OPERACION.put(OperacionEnum.RESTA, BigDecimal::subtract);
	}

	/**
	 * Recupera la operacion a realizar a partir del enumerado
	 * @param operacionEnum enumerado de operaciones 
	 * @return la operación {@link BinaryOperator}
	 */
	public BinaryOperator<BigDecimal> getOperation(OperacionEnum operacionEnum) {
		BinaryOperator<BigDecimal> operacion = MAP_OPERACION.get(operacionEnum);
		if (operacion == null) {
			throw new UnsupportedOperationException(String.format("Operación no implementada : %s " , operacionEnum.name()));
		}
		return operacion;
	}

}
