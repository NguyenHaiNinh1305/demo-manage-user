package com.vti.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SigninDTO {
	
	@NotBlank(message = "Username can not be a blank")
	@NotNull(message = "Username can not be null")
	private String username;
	
	@Length(min = 6, max = 20, message = "Password must has between 6 , 20 characters")
	private String password;
}
