package com.vti.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AccountDTO {
	private int id;
	
	private String username;
	
	//private String password;
	
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String role;
	
	private int departmentId;
	
	private String departmentName;
	
//	private DpDTO department;
//	
//	@Data
//	static class DpDTO {
//		private int id;
//		private String name;
//		
//		@JsonFormat(pattern = "dd-MM-yyyy")
//		private Date createdAt;
//	}
}
