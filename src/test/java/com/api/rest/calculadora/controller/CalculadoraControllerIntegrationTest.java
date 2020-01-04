package com.api.rest.calculadora.controller;

import java.math.BigDecimal;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import com.api.rest.calculadora.ApiRestCalculadoraApplication;
import com.api.rest.calculadora.enumeration.OperacionEnum;
import com.api.rest.calculadora.model.Operacion;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Junit de la clase {@linkplain CalculadoraController}
 * 
 * @author msanz
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { ApiRestCalculadoraApplication.class })
@AutoConfigureMockMvc
public class CalculadoraControllerIntegrationTest {

	/** Mock Mvc */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * JUnit de la operación resta
	 * 
	 * @throws Exception
	 */
	@Test
	public void testResta() throws Exception {
		BigDecimal expected = new BigDecimal(0.999999999999999999999999);
		BigDecimal error = new BigDecimal(0.000001);
		
		BigDecimal resultado = post(
				builderOperacion(new BigDecimal(1.999999999999999999999999), new BigDecimal(1.0000000000001), "-"));

		MatcherAssert.assertThat(expected, Matchers.is(Matchers.closeTo(resultado, error)));
	}

	/**
	 * JUnit de la operación suma
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSuma() throws Exception {
		BigDecimal expected = new BigDecimal(2);
		BigDecimal error = new BigDecimal(0.000001);

		BigDecimal resultado = post(builderOperacion(new BigDecimal(1.999999999999999999999),
				new BigDecimal(0.000000000000000000001), "+"));
	
		MatcherAssert.assertThat(expected, Matchers.is(Matchers.closeTo(resultado, error)));
	}

	/**
	 * JUnit de la operación multiplicación
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMultiplicaionUnsupportedOperationException() throws Exception {

		UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			postException(builderOperacion(new BigDecimal(1), new BigDecimal(1), "x"));
		});

		Assertions.assertEquals(String.format("Operación no implementada : %s ", OperacionEnum.MULTIPLICACION.name()),
				exception.getMessage());

		exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			postException(builderOperacion(new BigDecimal(1), new BigDecimal(1), "X"));
		});

		Assertions.assertEquals(String.format("Operación no implementada : %s ", OperacionEnum.MULTIPLICACION.name()),
				exception.getMessage());

	}

	/**
	 * JUnit de la operación división
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDivisionUnsupportedOperationException() throws Exception {

		final UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			postException(builderOperacion(new BigDecimal(1), new BigDecimal(1), "/"));
		});

		Assertions.assertEquals(String.format("Operación no implementada : %s ", OperacionEnum.DIVISION.name()),
				exception.getMessage());
	}

	/**
	 * JUnit de la operación resto
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRestoUnsupportedOperationException() throws Exception {
		final UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			postException(builderOperacion(new BigDecimal(1), new BigDecimal(1), "%"));
		});

		Assertions.assertEquals(String.format("Operación no implementada : %s ", OperacionEnum.RESTO.name()),
				exception.getMessage());

	}
	
	/**
	 * JUnit IllegalArgumentException
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIllegalArgumentException() throws Exception {
		IllegalArgumentException exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> {
			postException(builderOperacion( new BigDecimal(1), new BigDecimal(1), "k"));
		});

		Assertions.assertEquals(String.format("Operación no encontrada : %s.", "k"),
				exception.getMessage());
		
		exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> {
			postException(builderOperacion( new BigDecimal(1), new BigDecimal(1), ""));
		});

		Assertions.assertEquals(String.format("Operación no encontrada : %s.", ""),
				exception.getMessage());
		
		exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> {
			postException(builderOperacion( new BigDecimal(1), new BigDecimal(1), null));
		});

		Assertions.assertEquals(String.format("Operación no encontrada : %s.", ""),
				exception.getMessage());
		
		
		exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> {
			postException(builderOperacion(null, new BigDecimal(1), OperacionEnum.SUMA.getOperacion()));
		});

		Assertions.assertEquals("El operador 1 no es válido.",
				exception.getMessage());
		
		exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> {
			postException(builderOperacion(new BigDecimal(1),null, OperacionEnum.SUMA.getOperacion()));
		});

		Assertions.assertEquals("El operador 2 no es válido.",
				exception.getMessage());

	}

	/**
	 * Método que realiza el post de la api
	 * 
	 * @param operacion
	 *            operación a realizar {@link Operacion}
	 * @return resultado de la operación
	 * @throws Exception
	 *             en caso de error
	 */
	private BigDecimal post(final Operacion operacion) throws Exception {

		final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/calcula")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(operacion)));
		result.andExpect(MockMvcResultMatchers.status().isOk());

		MvcResult mvcResult = result.andReturn();

		return new BigDecimal(mvcResult.getResponse().getContentAsString());

	}

	/**
	 * Método que realiza el post de la api
	 * 
	 * @param operacion
	 *            operación a realizar {@link Operacion}
	 * @return resultado de la operación
	 * @throws Exception
	 *             en caso de error
	 */
	private void postException(final Operacion operacion) throws Exception {

		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/api/calcula").contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(operacion))).andExpect(MockMvcResultMatchers.status().isBadRequest());
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}

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
	 * @return Objeto Operacion
	 */
	private Operacion builderOperacion(BigDecimal operador1, BigDecimal operador2, String tipoOperacion) {
		Operacion operacion = Operacion.builder().operador1(operador1).operador2(operador2).tipoOperacion(tipoOperacion)
				.build();
		return operacion;
	}

	/**
	 * Método que permite generar el json de un objeto
	 * 
	 * @param obj
	 *            objeto a generar en json
	 * @return json del objeto
	 */
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
