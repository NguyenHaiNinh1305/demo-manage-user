package com.vti.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request
			) {
		
		Map<String, String> errors = new HashMap<>();
		
		for (ObjectError error: ex.getBindingResult().getAllErrors()) {
			String fileName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			
			errors.put(fileName, message);
		}
		
		return new ResponseEntity<>(errors, status);
		
	}
}
