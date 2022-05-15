package com.vti.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignupDTO {
	@NotEmpty(message = "Username can not be empty")
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	@Email(message = "Malformed email")
	private String email;
	
	@Length(min = 6, max = 20, message = "Password must has between 6 , 20 characters")
	private String password;
}
