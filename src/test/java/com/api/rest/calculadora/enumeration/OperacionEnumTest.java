package com.api.rest.calculadora.enumeration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Junit de la clase {@link OperacionEnum}
 * @author msanz
 *
 */
class OperacionEnumTest {

	
	/**
	 *  Junit IllegalArgumentException
	 */
	@Test
	void testIllegalArgumentException() {
		
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {			
			OperacionEnum.fromValue("d");
		});
		Assertions.assertEquals(String.format("Operación no encontrada : %s.", "d"),
				exception.getMessage());
		
		exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {			
			OperacionEnum.fromValue("suma");
		});
		Assertions.assertEquals(String.format("Operación no encontrada : %s.", "suma"),
				exception.getMessage());
		
		exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {			
			OperacionEnum.fromValue(null);
		});
		Assertions.assertEquals(String.format("Operación no encontrada : %s.", ""),
				exception.getMessage());
		
		exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {			
			OperacionEnum.fromValue("");
		});
		Assertions.assertEquals(String.format("Operación no encontrada : %s.", ""),
				exception.getMessage());
	}
	
	/**
	 *  Junit Enumerado suma
	 */
	@Test
	void testSuma() {
			
		assertEquals(OperacionEnum.SUMA, OperacionEnum.fromValue("+"));
	
	}
	
	/**
	 *  Junit Enumerado resta
	 */
	@Test
	void testResta() {
			
		assertEquals(OperacionEnum.RESTA, OperacionEnum.fromValue("-"));
	
	}
	
	/**
	 *  Junit Enumerado multiplicación
	 */
	@Test
	void testMultiplicacion() {
			
		assertEquals(OperacionEnum.MULTIPLICACION, OperacionEnum.fromValue("x"));
		assertEquals(OperacionEnum.MULTIPLICACION, OperacionEnum.fromValue("X"));
	
	}
	
	/**
	 *  Junit Enumerado división
	 */
	@Test
	void testDivision() {
			
		assertEquals(OperacionEnum.DIVISION, OperacionEnum.fromValue("/"));
	
	}
	
	/**
	 *  Junit Enumerado resto
	 */
	@Test
	void testResto() {
			
		assertEquals(OperacionEnum.RESTO, OperacionEnum.fromValue("%"));
	
	}

}
