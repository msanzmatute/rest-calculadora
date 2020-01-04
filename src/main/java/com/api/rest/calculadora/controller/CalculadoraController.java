package com.api.rest.calculadora.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.calculadora.model.Operacion;
import com.api.rest.calculadora.service.CalculadoraService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Micro servicio de calculadora. Realiza operaciones aritméticas según los parámetros
 * 
 * 
 * @author msanz
 *
 */
@RestController
@RequestMapping("/api")
@Api(value = "Api Rest Calculadora")
public class CalculadoraController {
	
	/** Servicio calculadora*/
	@Autowired
	CalculadoraService calculadoraService;
	
	
	/**
	 * Micro servicio que realiza una operación entre los dos operadores pasados como parámetros {@linkplain Operacion}
	 * 
	 *
	 * @return resultado de la operación realizada
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/calcula", produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Realiza una operación aritmética entre dos operadores (@RequestBody Operación)", response = Integer.class)
	public ResponseEntity<BigDecimal> calcula(@ApiParam("modelo de la operación a realizar") @RequestBody Operacion operacion) {
		//Llamada al servicio calculadora
		return ResponseEntity.ok(calculadoraService.calcula(operacion));
	}
	

	
}
