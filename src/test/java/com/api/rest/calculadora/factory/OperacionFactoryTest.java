package com.api.rest.calculadora.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.rest.calculadora.enumeration.OperacionEnum;

/**
 * Junit de la clase {@link OperacionFactory}
 * @author msanz
 *
 */
@SpringBootTest
class OperacionFactoryTest {

	/**Clase a testear */
	@Autowired
	private OperacionFactory factory;
	
	/**
	 * Junit getOperation de la operación suma
	 */
	@Test
	void testSuma() {
		BigDecimal operador1 = new BigDecimal(0.2999999999999999999999999999999999999);
		BigDecimal operador2 = new BigDecimal(1.2);
		BinaryOperator<BigDecimal> expected = BigDecimal::add;
	
		assertEquals(expected.apply(operador1, operador2), factory.getOperation(OperacionEnum.SUMA).apply(operador1, operador2));
	
	}
	/**
	 * Junit getOperation de la operación resta
	 */
	@Test
	void testResta() {
		BigDecimal operador1 = new BigDecimal(1.2999999999999999999999999999999999999);
		BigDecimal operador2 = new BigDecimal(1.2);
		BinaryOperator<BigDecimal> expected = BigDecimal::subtract;
		assertEquals(expected.apply(operador1, operador2), factory.getOperation(OperacionEnum.RESTA).apply(operador1, operador2));
	
	}
	
	/**
	 * Junit UnsupportedOperationException
	 */
	@Test
	void testUnsupportedOperationException() {

		
		UnsupportedOperationException expectEx = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			factory.getOperation(OperacionEnum.MULTIPLICACION);
		});
		
		Assertions.assertEquals(String.format("Operación no implementada : %s " , OperacionEnum.MULTIPLICACION.name()), expectEx.getMessage());

		expectEx = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			factory.getOperation(OperacionEnum.DIVISION);
		});
		Assertions.assertEquals(String.format("Operación no implementada : %s " , OperacionEnum.DIVISION.name()), expectEx.getMessage());

		
		expectEx = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			factory.getOperation(OperacionEnum.RESTO);
		});
		Assertions.assertEquals(String.format("Operación no implementada : %s " , OperacionEnum.RESTO.name()), expectEx.getMessage());


	}


}
