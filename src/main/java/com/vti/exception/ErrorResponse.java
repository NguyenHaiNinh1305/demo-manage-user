package com.vti.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NonNull;

@Data
public class ErrorResponse {
	@NonNull
	private HttpStatus status;
	
	@NonNull
	private String message;
}
