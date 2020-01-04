/**
 * 
 */
package com.api.rest.calculadora.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.rest.calculadora.enumeration.OperacionEnum;
import com.api.rest.calculadora.factory.OperacionFactory;
import com.api.rest.calculadora.model.Operacion;

/**
 * Junit de la clase {@link CalculadoraService}
 * 
 * @author msanz
 *
 */
@SpringBootTest
class CalculadoraServiceTest {

	/** Clase a testear {@link OperacionFunction} */
	@InjectMocks
	private CalculadoraService calculadoraService;

	/** Mock Factoria de operaciones */
	@Mock
	private OperacionFactory factory;

	/** Agument captor para el metodo OperacionFactory.getOperation */
	@Captor
	ArgumentCaptor<OperacionEnum> argCaptor;

	/**
	 * Inicialización de mocks
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(calculadoraService);
	}

	/**
	 * Reset de mocks
	 */
	@AfterEach
	void tearDown() throws Exception {
		Mockito.reset(factory);
	}

	/**
	 * Test de la operacion resta
	 */
	@Test
	void testResta() {
		// GIVEN

		final BigDecimal operador1 = new BigDecimal(1.2);
		final BigDecimal operador2 = new BigDecimal(1.2);
		final String tipoOperacion = OperacionEnum.RESTA.getOperacion();

		final Operacion operacion = builderOperacion(operador1, operador2, tipoOperacion);
		final BinaryOperator<BigDecimal> resta = (a, b) -> a.subtract(b);
		final BigDecimal expected = resta.apply(operador1, operador2);
		Mockito.when(factory.getOperation(ArgumentMatchers.any(OperacionEnum.class))).thenReturn(resta);
		// WHEN
		final BigDecimal result = calculadoraService.calcula(operacion);

		// THEN
		Mockito.verify(factory).getOperation(argCaptor.capture());
		Assertions.assertEquals(expected, result);
		Assertions.assertEquals(OperacionEnum.RESTA, argCaptor.getValue());

	}

	/**
	 * Test de la operacion suma
	 */
	@Test
	void testSuma() {
		// GIVEN
		final BigDecimal operador1 = new BigDecimal(1.2);
		final BigDecimal operador2 = new BigDecimal(1.2);
		final String tipoOperacion = OperacionEnum.SUMA.getOperacion();

		final Operacion operacion = builderOperacion(operador1, operador2, tipoOperacion);
		final BinaryOperator<BigDecimal> suma = (a, b) -> a.add(b);
		final BigDecimal expected = suma.apply(operador1, operador2);
		Mockito.when(factory.getOperation(ArgumentMatchers.any(OperacionEnum.class))).thenReturn(suma);

		// WHEN
		final BigDecimal result = calculadoraService.calcula(operacion);

		// THEN
		Mockito.verify(factory).getOperation(argCaptor.capture());
		Assertions.assertEquals(expected, result);
		Assertions.assertEquals(OperacionEnum.SUMA, argCaptor.getValue());

	}

	/**
	 * Junit de las operaciones no implementadas
	 */
	@Test
	void testGetOperationUnsupportedOperationException() {

		// GIVEN
		final BigDecimal operador1 = new BigDecimal(1.2);
		final BigDecimal operador2 = new BigDecimal(1.2);

		final List<String> operacionesExpected = Arrays.stream(OperacionEnum.values()).map(OperacionEnum::getOperacion)
				.collect(Collectors.toList());

		final String expected = "Operación no implementada";
		Mockito.when(factory.getOperation(ArgumentMatchers.any(OperacionEnum.class)))
				.thenThrow(new UnsupportedOperationException(expected));

		// THEN
		UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			calculadoraService.calcula(builderOperacion(operador1, operador2, OperacionEnum.DIVISION.getOperacion()));
		});
		Assertions.assertEquals(expected, exception.getMessage());

		exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			calculadoraService
					.calcula(builderOperacion(operador1, operador2, OperacionEnum.MULTIPLICACION.getOperacion()));
		});
		Assertions.assertEquals(expected, exception.getMessage());

		exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			calculadoraService.calcula(builderOperacion(operador1, operador2, "+"));
		});
		Assertions.assertEquals(expected, exception.getMessage());

		exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			calculadoraService.calcula(builderOperacion(operador1, operador2, OperacionEnum.RESTA.getOperacion()));
		});
		Assertions.assertEquals(expected, exception.getMessage());

		exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			calculadoraService.calcula(builderOperacion(operador1, operador2, OperacionEnum.RESTO.getOperacion()));
		});
		Assertions.assertEquals(expected, exception.getMessage());

		Mockito.verify(factory, Mockito.times(5)).getOperation(argCaptor.capture());
		final List<String> operaciones = Arrays.stream(OperacionEnum.values()).map(OperacionEnum::getOperacion)
				.collect(Collectors.toList());
		Assertions.assertTrue(operacionesExpected.containsAll(operaciones));
		

	}

	/**
	 * Junit IllegalArgumentException
	 */
	@Test
	void testIllegalArgumentException() {

		// GIVEN
		final BigDecimal operador1 = new BigDecimal(1.2);
		final BigDecimal operador2 = new BigDecimal(1.2);

		final BinaryOperator<BigDecimal> suma = (a, b) -> a.add(b);

		Mockito.when(factory.getOperation(ArgumentMatchers.any(OperacionEnum.class))).thenReturn(suma);

		// THEN
		IllegalArgumentException expectEx = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			calculadoraService.calcula(builderOperacion(null, null, OperacionEnum.SUMA.getOperacion()));
		});

		Assertions.assertEquals("El operador 1 no es válido.", expectEx.getMessage());

		expectEx = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			calculadoraService.calcula(builderOperacion(null, null, OperacionEnum.RESTA.getOperacion()));
		});
		Assertions.assertEquals("El operador 1 no es válido.", expectEx.getMessage());

		expectEx = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			calculadoraService.calcula(builderOperacion(operador1, null, OperacionEnum.RESTA.getOperacion()));
		});
		Assertions.assertEquals("El operador 2 no es válido.", expectEx.getMessage());

		expectEx = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			calculadoraService.calcula(builderOperacion(null, operador2, OperacionEnum.RESTA.getOperacion()));
		});
		Assertions.assertEquals("El operador 1 no es válido.", expectEx.getMessage());

		expectEx = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			calculadoraService.calcula(builderOperacion(null, null, null));
		});
		Assertions.assertEquals("El operador 1 no es válido.", expectEx.getMessage());

		expectEx = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			calculadoraService.calcula(builderOperacion(null, operador2, OperacionEnum.DIVISION.getOperacion()));
		});

		expectEx = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			calculadoraService.calcula(builderOperacion(null, operador2, OperacionEnum.MULTIPLICACION.name()));
		});
		Assertions.assertEquals("El operador 1 no es válido.", expectEx.getMessage());

		expectEx = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			calculadoraService.calcula(builderOperacion(null, operador2, OperacionEnum.RESTO.name()));
		});
		Assertions.assertEquals("El operador 1 no es válido.", expectEx.getMessage());

		expectEx = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			calculadoraService.calcula(builderOperacion(operador1, operador2, null));
		});

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			calculadoraService.calcula(builderOperacion(operador1, operador2, "v"));
		});

		Mockito.verifyNoInteractions(factory);
	}

	/**
	 * Builder del objeto {@link Operacion}
	 * 
	 * @param operador1
	 *            operador 1
	 * @param operador2
	 *            operador 2
	 * @param tipoOperacion
	 *            tipo de la operacion
	 * @return Objeto {@link Operacion}
	 */
	private Operacion builderOperacion(BigDecimal operador1, BigDecimal operador2, String tipoOperacion) {
		return Operacion.builder().operador1(operador1).operador2(operador2).tipoOperacion(tipoOperacion).build();
	}

}
