package com.javaweb.controllerAdvice;

import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.javaweb.model.ErrorResponseDTO;

import customexception.FieldRequireException;


@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<Object> handleArithmeticException(
			ArithmeticException ex, WebRequest request) {
		
		ErrorResponseDTO error = new ErrorResponseDTO();
		error.setError(ex.getMessage());
		List<String> detail = new ArrayList<String>();
		detail.add("check lai name hoac numberofbasement because null");
		error.setDetail(detail); 
		
	    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FieldRequireException.class)
	public ResponseEntity<Object> handleFieldRequireException(
			FieldRequireException ex, WebRequest request) {
		
		ErrorResponseDTO error = new ErrorResponseDTO();
		error.setError(ex.getMessage());
		List<String> detail = new ArrayList<String>();
		detail.add("check lai name hoac numberofbasement because null");
		error.setDetail(detail);
		
	    return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
	}

}
