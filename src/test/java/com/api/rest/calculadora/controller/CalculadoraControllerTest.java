package com.api.rest.calculadora.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.math.BigDecimal;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.rest.calculadora.model.Operacion;
import com.api.rest.calculadora.service.CalculadoraService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Junit de la clase {@linkplain CalculadoraController}
 * 
 * @author msanz
 *
 */
class CalculadoraControllerTest {

	/** Mock Mvc*/
	private MockMvc mockMvc;

   
	/** Clase a testear*/
    @InjectMocks
    private CalculadoraController calculadoraController;
    
    /** Mock servicio calculadora*/
    @Mock
    private CalculadoraService calculadoraService;
    
    /** Agument captor para el método CalculadoraService.calculadoraService */
	@Captor
	ArgumentCaptor<Operacion> argCaptor;

  
	/**
	 * Inicialización de mocks
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		 MockitoAnnotations.initMocks(this);
		
	        mockMvc = MockMvcBuilders
	                .standaloneSetup(calculadoraController)
	                .build();
	}

	/**
	 * Reset de mocks
	 * 
	 * @throws Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		Mockito.reset(calculadoraService);
	}

	/**
	 * JUnit de la operación resta
	 * 
	 * @throws Exception
	 */
	@Test
	public void testResta() throws Exception {
		BigDecimal expected = new BigDecimal(1);
	    Mockito.when(calculadoraService.calcula(ArgumentMatchers.any(Operacion.class))).thenReturn(expected);
	    BigDecimal resultado = post(builderOperacion(new BigDecimal(1), new BigDecimal(1), "-"));
	    verify(calculadoraService).calcula(argCaptor.capture());
	    Assertions.assertEquals(expected, resultado);
	    verifyNoMoreInteractions(calculadoraService);
	}
	
	/**
	 * JUnit de la operación suma
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSuma() throws Exception {
		BigDecimal expected = new BigDecimal(1);
	    Mockito.when(calculadoraService.calcula(ArgumentMatchers.any(Operacion.class))).thenReturn(expected);
	    BigDecimal resultado = post(builderOperacion(new BigDecimal(1), new BigDecimal(1), "+"));
	    verify(calculadoraService).calcula(argCaptor.capture());
	    Assertions.assertEquals(expected, resultado);
	    verifyNoMoreInteractions(calculadoraService);
	}
	
	/**
	 * JUnit de la operación multiplicación
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMultiplicaion() throws Exception {
		BigDecimal expected = new BigDecimal(1);
	    Mockito.when(calculadoraService.calcula(ArgumentMatchers.any(Operacion.class))).thenReturn(expected);
	    BigDecimal resultado = post(builderOperacion(new BigDecimal(1), new BigDecimal(1), "x"));
	    verify(calculadoraService).calcula(argCaptor.capture());
	    Assertions.assertEquals(expected, resultado);
	    verifyNoMoreInteractions(calculadoraService);
	}
	
	/**
	 * JUnit de la operación división
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDivision() throws Exception {
		BigDecimal expected = new BigDecimal(1);
	    Mockito.when(calculadoraService.calcula(ArgumentMatchers.any(Operacion.class))).thenReturn(expected);
	    BigDecimal resultado = post(builderOperacion(new BigDecimal(1), new BigDecimal(1), "/"));
	    verify(calculadoraService).calcula(argCaptor.capture());
	    Assertions.assertEquals(expected, resultado);
	    verifyNoMoreInteractions(calculadoraService);
	}
	
	/**
	 * JUnit de la operación resto
	 * 
	 * @throws Exception
	 */
	@Test
	public void testResto() throws Exception {
		BigDecimal expected = new BigDecimal(1);
	    Mockito.when(calculadoraService.calcula(ArgumentMatchers.any(Operacion.class))).thenReturn(expected);
	    BigDecimal resultado = post(builderOperacion(new BigDecimal(1), new BigDecimal(1), "%"));
	    verify(calculadoraService).calcula(argCaptor.capture());
	    Assertions.assertEquals(expected, resultado);
	    verifyNoMoreInteractions(calculadoraService);
	}
	
	
	/**
	 * Método que realiza el post de la api
	 * @param operacion operación a realizar {@link Operacion}
	 * @return resultado de la operación
	 * @throws Exception en caso de error
	 */
	private BigDecimal post(final Operacion operacion) throws Exception {
		
		final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/calcula")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(operacion)));
		result.andExpect(MockMvcResultMatchers.status().isOk());

		MvcResult mvcResult = result.andReturn();

		return new BigDecimal(mvcResult.getResponse().getContentAsString());

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
	 * @param obj objeto a generar en json
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
