package com.vti.form;

import lombok.Data;

@Data
public class AccountForm {
	private int id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	private int departmentId;
}
