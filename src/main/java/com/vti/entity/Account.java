package com.vti.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "`Account`", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "username")
})
@Data
public class Account {
	
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username", length = 50, nullable = false)
	private String username;

	@Column(name = "password", length = 50, nullable = false)
	private String password;
	
	@Column(name = "email", length = 50, nullable = false)
	private String email;
	
	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;
	
	@Column(name = "role", columnDefinition = "ENUM('Admin', 'Employee', 'Manager')")
	@Enumerated(EnumType.STRING)
	private AccountRole role;

	public enum AccountRole {
		Admin, Employee, Manager;
		
		public static AccountRole toEnum(String role) {
			for (AccountRole item : AccountRole.values()) {
				if (item.toString().equals(role)) return item;
			}
			return null;
		}
		
	}
	
//	@ManyToMany
//	@JoinTable(name = "AccountDepartment",
//		joinColumns = { @JoinColumn(name = "account_id") },
//		inverseJoinColumns = { @JoinColumn(name = "department_id") }
//	)
//	private List<Department> departments;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	
	@Override
	public String toString() {
		return "[id = " + id + "; username = " + username + "; role = " + role + "]";
	}
	
}
